package com.example.project_1team_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class differentLocations extends AppCompatActivity {
    ImageButton btnAddCity;
    EditText etSearchCity;
    RecyclerView locationList;
    ArrayList<Locations> locationsArrayList;
    ArrayList<String> populatCityList = new ArrayList<>(Arrays.asList("New York", "London", "Paris"));

    String searchName = "";
    final String API_KEY = "wd6NvdetIIAjGGQP1GzsuRiKyKXEoBL7";
    String city;
    String localTime;
    String forecastInfo;
    String temperature;
    String tempHighest;
    String tempLowest;
    RequestQueue queue;
    LocationAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_different_locations);
        locationsArrayList = new ArrayList<>();
        locationList = findViewById(R.id.locationList);
        queue = Volley.newRequestQueue(this);
        btnAddCity = findViewById(R.id.btnAddCity);
        etSearchCity = findViewById(R.id.etSearchCity);

        populatePolularCities();

        btnAddCity.setOnClickListener(view -> {
            populatCityList.add(String.valueOf(etSearchCity.getText()));
            Log.d("fgfwef", String.valueOf(populatCityList));

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
                        String locationURL1 = "https://dataservice.accuweather.com/currentconditions/v1/" + key + "?apikey=" + API_KEY;
                        localTime = localTime2;
                        convertToEST(localTime);


                        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, locationURL1, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject jName = response.getJSONObject(0);
                                    temperature = ((int) Double.parseDouble(jName.getJSONObject("Temperature").getJSONObject("Imperial").getString("Value"))) + "°";
                                    forecastInfo = jName.getString("WeatherText");


                                    Log.d("testing", forecastInfo);
                                    String url = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + key + "?apikey=" + API_KEY;
                                    JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                tempHighest = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                                                tempLowest = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");
                                                locationsArrayList.add(new Locations(city, localTime, forecastInfo, tempHighest, tempLowest, temperature));
                                                adapter.notifyDataSetChanged();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, error -> Log.d("uyuy", "Error: " + error.getMessage()));
                                    queue.add(request2);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, error -> Log.d("uyuy", "onErrorResponse: " + error.getMessage()));
                        queue.add(request1);
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("uyuy", error.toString());
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

        System.out.println(globalFormat.format(currentETime));
        localTime = globalFormat.format(currentETime);
    }



    private void populatePolularCities() {

        new Thread(() -> {
            try {
                for(int i=0;i<populatCityList.size();i++){
                    int finalI = i;
                    runOnUiThread(() -> fetchData(populatCityList.get(finalI)));
                    Thread.sleep(800);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}