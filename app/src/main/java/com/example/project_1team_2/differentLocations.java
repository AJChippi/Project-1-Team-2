package com.example.project_1team_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class differentLocations extends AppCompatActivity {
    ImageButton btnAddCity;
    EditText etSearchCity;
    RecyclerView locationList;
    ArrayList<Locations> locationsArrayList;
    ArrayList<String> populatCityList = new ArrayList<>(Arrays.asList("New York", "London", "Paris"));

    String searchName = "";
    final String API_KEY = "3knmwx4pZle8RVL2K78nks9zZ3Jxmlkn";
    String myTag = "MY_APP";
    String city;
    String localTime;
    String forecastInfo;
    String temperature;
    String tempHighest;
    String tempLowest;
    RequestQueue queue;
    LocationAdapter adapter;
    SharedPreferences sharedPreferences;
    String isMetric = "&metric=";
    boolean boolMetric =false;
    SharedPreferences settingsPref;
    String metric="Metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_different_locations);
        locationsArrayList = new ArrayList<>();
        locationList = findViewById(R.id.locationList);
        queue = Volley.newRequestQueue(this);
        btnAddCity = findViewById(R.id.btnAddCity);
        etSearchCity = findViewById(R.id.etSearchCity);
        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);





        settingsPref = this.getSharedPreferences(this.getResources().getString(R.string.settings_preferences_file_key), Context.MODE_PRIVATE);
        String getUnitPreference = settingsPref.getString(getResources().getString(R.string.settings_unit_key),"F");
        switch (getUnitPreference){
            case("F"):
                isMetric += "false";
                boolMetric = false;
                metric="Imperial";
                break;
            case "C":
                boolMetric = true;
                isMetric += "true";
        }
        Log.d("FAVPAGE", isMetric);





        /**
         * Retrieve list of popular cities from shared preferences
         */
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Set", "");
        if (json.isEmpty()) {
            Toast.makeText(this,"There is something error",Toast.LENGTH_LONG).show();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            populatCityList = gson.fromJson(json, type);
            for (String data : populatCityList) {
            }
        }
        populatePolularCities();


        /**
         * Once button clicked,
         */
        btnAddCity.setOnClickListener(view -> {
            populatCityList.add(String.valueOf(etSearchCity.getText()));
            Log.d("fgfwef", String.valueOf(populatCityList));
            Toast.makeText(this,etSearchCity.getText()+" added to favourite list",Toast.LENGTH_SHORT).show();
            fetchData(String.valueOf(etSearchCity.getText()));
            etSearchCity.setText("");
        });







        /**
         * Swipe to remove item from location list
         */
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                locationsArrayList.remove(viewHolder.getLayoutPosition());
                populatCityList.remove(viewHolder.getLayoutPosition());
                adapter.notifyDataSetChanged();

            }
        });
        helper.attachToRecyclerView(locationList);


    }

    /**
     * This function will fetch data from API and populate data
     * to the Recycle List.
     *
     * @param cityName will pass user input for requested city
     */
    public void fetchData(String cityName) {
        String locationURL = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + API_KEY + "&q=" + (searchName.length() == 0 ? cityName : searchName);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                locationURL,
                null,
                response -> {
                    try {
                        JSONObject jName = response.getJSONObject(0);
                        city = jName.getString("LocalizedName");
                        String localTime2 = jName.getJSONObject("TimeZone").getString("Name");

                        String key = jName.getString("Key");
                        String locationURL1 = "https://dataservice.accuweather.com/currentconditions/v1/" + key + "?apikey=" + API_KEY + isMetric;
                        localTime = localTime2;
                        convertToEST(localTime);

                        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, locationURL1, null, response1 -> {
                            try {
                                JSONObject jName1 = response1.getJSONObject(0);
                                temperature = ((int) Double.parseDouble(jName1.getJSONObject("Temperature").getJSONObject(metric).getString("Value"))) + "°";
                                forecastInfo = jName1.getString("WeatherText");
                                String url = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + key + "?apikey=" + API_KEY + isMetric;
                                JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null, response2 -> {
                                    try {
                                        tempHighest = response2.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                                        tempLowest = response2.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");
                                        locationsArrayList.add(new Locations(city, localTime, forecastInfo, tempHighest, tempLowest, temperature));
                                        adapter.notifyDataSetChanged();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }, error -> Log.d(myTag, "Error: " + error.getMessage()));
                                queue.add(request2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> Log.d(myTag, "onErrorResponse: " + error.getMessage()));
                        queue.add(request1);
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d(myTag, error.toString());
                    error.printStackTrace();
                }
        );

        queue.add(request);


        adapter = new LocationAdapter(locationsArrayList, this);
        locationList.setAdapter(adapter);
        locationList.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    public void convertToEST(String timeCode) throws ParseException {
        DateTimeFormatter globalFormat = DateTimeFormatter.ofPattern("hh:mma z");
        ZonedDateTime currentISTime = ZonedDateTime.now();
        ZonedDateTime currentETime = currentISTime.withZoneSameInstant(ZoneId.of(timeCode)); //ET Time
        localTime = globalFormat.format(currentETime);
    }



    private void populatePolularCities() {
        new Thread(() -> {
            try {
                for(int i=0;i<populatCityList.size();i++){
                    int finalI = i;
                    runOnUiThread(() -> fetchData(populatCityList.get(finalI)));
                    Thread.sleep(1200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Gson gson = new Gson();
        String json = gson.toJson(populatCityList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Set",json );
        editor.commit();
    }
}