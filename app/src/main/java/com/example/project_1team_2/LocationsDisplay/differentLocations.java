package com.example.project_1team_2.LocationsDisplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_1team_2.LocationsDisplay.LocationAdapter;
import com.example.project_1team_2.LocationsDisplay.Locations;
import com.example.project_1team_2.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    ImageButton btnAddCity;
    EditText etSearchCity;
    RecyclerView locationList;
    ArrayList<Locations> locationsArrayList;
    ArrayList<String> populatCityList = new ArrayList<>(Arrays.asList("New York", "London", "Paris"));

    String API_KEY = "";
    String myTag = "MY_APP";
    RequestQueue queue;
    LocationAdapter adapter;
    SharedPreferences sharedPreferences;
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


        //Update temperature metric info from shared preferences
        settingsPref = this.getSharedPreferences(this.getResources().getString(R.string.settings_preferences_file_key), Context.MODE_PRIVATE);
        String getUnitPreference = settingsPref.getString(getResources().getString(R.string.settings_unit_key),"F");
        switch (getUnitPreference){
            case("F"):
                metric="Imperial";
                break;
            case "C":
                metric="Metric";
        }

        //Get api key from main activity
        Intent i = getIntent();
        API_KEY = i.getStringExtra("API_KEY");


        //Retrieve list of popular cities from shared preferences
        Gson gson = new Gson();
        String json = sharedPreferences.getString("SavedList", "");
        if (json.isEmpty()) {
            Toast.makeText(this, "No saved cities", Toast.LENGTH_SHORT).show();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            populatCityList = gson.fromJson(json, type);
        }

        //populate cities (saved or default)
        populatePolularCities();


        /**
         * Once button clicked, it will add the city name from user to
         * favourite city list.
         */
        btnAddCity.setOnClickListener(view -> {
            populatCityList.add(String.valueOf(etSearchCity.getText()));
            locationsArrayList.add(new Locations(etSearchCity.getText() + "", "", "", "", "", "", ""));
            Toast.makeText(this,etSearchCity.getText()+" added to favourite list",Toast.LENGTH_SHORT).show();
            fetchData(String.valueOf(etSearchCity.getText()), locationsArrayList.size() - 1);
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
                locationsArrayList.remove(viewHolder.getLayoutPosition());      //removes city from recycle list
                populatCityList.remove(viewHolder.getLayoutPosition());     //removes city from favourite city list
                adapter.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(locationList);


    }


    /**
     * This function will fetch data from API and populate dat to the Recycle List.
     *
     * @param cityName will pass user input for requested city
     * @param index will point to the item where city name is stored in Location class
     */
    public void fetchData(String cityName, int index) {
        String locationURL = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + API_KEY + "&q="+cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                locationURL,
                null,
                response -> {
                    try {
                        //API call to get city info
                        JSONObject jName = response.getJSONObject(0);
                        String localTime2 = jName.getJSONObject("TimeZone").getString("Name");  //fetching local time from API
                        String countryName = jName.getJSONObject("Country").getString("LocalizedName"); //fetching country name from API

                        String key = jName.getString("Key"); //country key to get weather info
                        String localTime = localTime2;
                        localTime = convertTime(localTime);
                        String finalLocalTime = localTime;

                        //API call to get weather info
                        String locationURL1 = "https://dataservice.accuweather.com/currentconditions/v1/" + key + "?apikey=" + API_KEY;
                        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, locationURL1, null, response1 -> {
                            try {
                                JSONObject jName1 = response1.getJSONObject(0);
                                String temperature = ((int) Double.parseDouble(jName1.getJSONObject("Temperature").getJSONObject(metric).getString("Value"))) + "°";
                                String forecastInfo = jName1.getString("WeatherText");

                                //API call to get high, low temperature info
                                String url = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + key + "?apikey=" + API_KEY;
                                JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null, response2 -> {
                                    try {
                                        String tempHighest = response2.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                                        String tempLowest = response2.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");

                                        // Update location info
                                        Locations location = locationsArrayList.get(index);
                                        location.localTime = finalLocalTime;
                                        location.forecastInfo = forecastInfo;
                                        location.countryName = countryName;
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

        //adapter to inflate recycle list
        adapter = new LocationAdapter(locationsArrayList, this);
        locationList.setAdapter(adapter);
        locationList.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    /**
     * This function passes the TimeZone name from API and gets the current time
     * of the TimeZone name using ZonedDataTime. It is formatted using
     * DateTimeFormatter.
     *
     * @param timeCode passed the name of TimeZone (eg. America/Detroid)
     * @return formatted local time of the time zone passed
     */
    public String convertTime(String timeCode) throws ParseException {
        DateTimeFormatter globalFormat = DateTimeFormatter.ofPattern("hh:mma");
        ZonedDateTime currentTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of(timeCode)); //ET Time
        String localTime = globalFormat.format(currentTime);
        return localTime;
    }


    /**
     * This function populates the list of cities for the favorite city view.
     */
    private void populatePolularCities() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {

            // Initialize the city list.
            runOnUiThread(() -> {
                for (String location : populatCityList) {
                    locationsArrayList.add(new Locations(location, "","", "", "", "", ""));
                }
            });

            for(int i = 0; i< populatCityList.size(); i++){
                int finalI = i;
                runOnUiThread(()-> fetchData(populatCityList.get(finalI), finalI));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //using gson to save arraylist in shared preferences
        Gson gson = new Gson();
        String json = gson.toJson(populatCityList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SavedList",json );
        editor.commit();
    }
}