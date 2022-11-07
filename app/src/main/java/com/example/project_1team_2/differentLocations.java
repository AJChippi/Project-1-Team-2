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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class differentLocations extends AppCompatActivity {
    ProgressBar progressBar;
    ImageButton btnAddCity;
    EditText etSearchCity;
    TextView txtInfo;
    RecyclerView locationList;
    ArrayList<Locations> locationsArrayList;
    ArrayList<String> populatCityList = new ArrayList<>(Arrays.asList("New York", "London", "Paris"));

    final String API_KEY = "3xQAllwW22tptHIF5iGKyRXZXf0fKWK1";
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
        progressBar=findViewById(R.id.progressBar);
        txtInfo = findViewById(R.id.txtInfo);



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
        txtInfo.setText("Swipe left on location to remove");



        /**
         * Once button clicked,
         */
        btnAddCity.setOnClickListener(view -> {
            populatCityList.add(String.valueOf(etSearchCity.getText()));
            locationsArrayList.add(new Locations(etSearchCity.getText() + "", "", "", "", "", ""));
            Toast.makeText(this,etSearchCity.getText()+" added to favourite list",Toast.LENGTH_SHORT).show();
            fetchData(String.valueOf(etSearchCity.getText()), locationsArrayList.size() - 1);
            Log.d("asfasfasfas", String.valueOf(locationList.getAdapter().getItemCount()));

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
                Toast.makeText(getApplicationContext(),"Location removed",Toast.LENGTH_SHORT).show();
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
    public void fetchData(String cityName, int index) {
        String locationURL = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + API_KEY + "&q="+cityName;

        Log.d("testing", "start of fetch: " + cityName);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                locationURL,
                null,
                response -> {
                    try {
                        JSONObject jName = response.getJSONObject(0);
                        city = jName.getString("LocalizedName");
                        String localTime2 = jName.getJSONObject("TimeZone").getString("Name");
                        Log.d("asfsfaf", city + "\t"+ populatCityList);

                        String key = jName.getString("Key");
                        String locationURL1 = "https://dataservice.accuweather.com/currentconditions/v1/" + key + "?apikey=" + API_KEY + isMetric;
                        String localTime = localTime2;
                        convertToEST(localTime);

                        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, locationURL1, null, response1 -> {
                            try {
                                JSONObject jName1 = response1.getJSONObject(0);
                                String temperature = ((int) Double.parseDouble(jName1.getJSONObject("Temperature").getJSONObject(metric).getString("Value"))) + "°";
                                String forecastInfo = jName1.getString("WeatherText");
                                String url = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + key + "?apikey=" + API_KEY + isMetric;
                                JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null, response2 -> {
                                    try {
                                        tempHighest = response2.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                                        tempLowest = response2.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");

                                        // Update location info
                                        Log.d("testing", index + "");
                                        Locations location = locationsArrayList.get(index);
                                        Log.d("testing", location.city);
                                        Log.d("testing", temperature);
                                        location.localTime = localTime;
                                        location.forecastInfo = forecastInfo;
                                        location.highestTemp = tempHighest;
                                        location.lowestTemp = tempLowest;
                                        location.temperature = temperature;
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
        Log.d("myTagsaf", "onErrorResponse: " + locationsArrayList.size());


    }

    public void convertToEST(String timeCode) throws ParseException {
        DateTimeFormatter globalFormat = DateTimeFormatter.ofPattern("hh:mma z");
        ZonedDateTime currentISTime = ZonedDateTime.now();
        ZonedDateTime currentETime = currentISTime.withZoneSameInstant(ZoneId.of(timeCode)); //ET Time
        localTime = globalFormat.format(currentETime);
    }


    /**
     * Populate the list of cities for the favorite city view.
     */
    private void populatePolularCities() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(() -> {

            // Initialize the city list.
            runOnUiThread(() -> {
                for (String location : populatCityList) {
                    Log.d("testing", location);
                    locationsArrayList.add(new Locations(location, "", "", "", "", ""));
                }
            });

            for(int i=0;i<populatCityList.size();i++){
                int finalI = i;


                runOnUiThread(()-> fetchData(populatCityList.get(finalI), finalI));
                runOnUiThread(()-> txtInfo.setText("Fetching data"));
                progressBar.setProgress(progressBar.getProgress()+(100/populatCityList.size()+1));

                runOnUiThread(()-> txtInfo.setText("Swipe left on location to remove"));

            }
            progressBar.setVisibility(View.INVISIBLE);



        });
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