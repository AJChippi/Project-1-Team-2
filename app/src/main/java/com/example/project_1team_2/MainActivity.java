package com.example.project_1team_2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.ImageButton;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response; 
import com.android.volley.VolleyError; 
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.project_1team_2.byDayDisplay.ByDay;
import com.example.project_1team_2.byDayDisplay.ByDayAdapter;
import com.example.project_1team_2.byHourDisplay.byHour;
import com.example.project_1team_2.byHourDisplay.byHourAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    ImageButton btnSettings, btnListFavorites, btnSatellite;

    TextView txtDate, txtLocation, txtDegree, txtCondition, txtHighToLow;

    final String API_KEY = "5FnQmq7XLIV2nkGlUd7QXi6BdQgAfTg8";
    //by hour forecast list
    ArrayList<byHour> hourForecast;
    ArrayList<ByDay> byDayForecast;
    byHourAdapter byHourAdapter;
    ByDayAdapter byDayAdapter;
    // define this somewhere else
    RecyclerView lstByDay;
    RecyclerView lstByHour;


    RequestQueue queue;
    String myTag = "MY_APP";

    String searchName = "";
    String locationURL = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=5FnQmq7XLIV2nkGlUd7QXi6BdQgAfTg8&q="+(searchName.length()==0?"saginaw":searchName);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        queue = Volley.newRequestQueue(this);
        btnSettings = findViewById(R.id.btnSettings);
        btnListFavorites = findViewById(R.id.btnListFavorite);
        btnSatellite = findViewById(R.id.btnSatellite);
        lstByDay = findViewById(R.id.lstByDay);


        //By Hour Forecast
        setUpByHour();

        // By day forecast
        setUpByDay();



//        for (int i=0;i<20;i++){
//            byDay.add(new ByDay("Yesterday","60","30",R.drawable.satellite,20));
//        }

//        adapter.notifyDataSetChanged();

        txtDate = findViewById(R.id.txtDate);
        txtLocation = findViewById(R.id.txtLocation);
        txtDegree = findViewById(R.id.txtDegree);
        txtCondition = findViewById(R.id.txtCondition);
        txtHighToLow = findViewById(R.id.txtHighToLow);


        btnSettings.setOnClickListener(view ->{
            // go to settings page
            Intent intent = new Intent(MainActivity.this, settings.class);
            startActivity(intent);

        });

        btnListFavorites.setOnClickListener(view ->{
            // go to favorites page
            Intent intent = new Intent(MainActivity.this, differentLocations.class);
            startActivity(intent);

        });

        btnSatellite.setOnClickListener(view ->{
            // go to satellite page
            Intent intent = new Intent(MainActivity.this, Satellite.class);
            startActivity(intent);

        });


            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                    locationURL,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            JSONObject jName = null;
                            try {
                                jName = response.getJSONObject(0);
                                String name = jName.getString("LocalizedName");
                                String key = jName.getString("Key");
                                Log.d("testing", key);
                                String locationURL = "https://dataservice.accuweather.com/currentconditions/v1/" + key + "?apikey=VNJ7wu0YO9pEaab65xSSUjGeW2J72jnL";
                                txtLocation.setText(name);

                                JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, locationURL, null, new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        JSONObject jName = null;
                                        try {
                                            jName = response.getJSONObject(0);
                                            String imperial = ((int) Double.parseDouble(jName.getJSONObject("Temperature").getJSONObject("Imperial").getString("Value"))) +"°";
                                            String weatherText = jName.getString("WeatherText");
                                            txtDegree.setText(imperial);
                                            txtCondition.setText(weatherText);
                                            Log.d("testing", weatherText);
                                            String url = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + key + "?apikey=VNJ7wu0YO9pEaab65xSSUjGeW2J72jnL";
                                            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        String high = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                                                        String low = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");
                                                        txtHighToLow.setText( "High: "+high + "° - Low: " + low + "°");
                                                        Log.d("testing", low);

                                                        getByHourForecast(key);
                                                        getByDayForecast(key);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Log.d(myTag, "Error: " + error.getMessage());
                                                }
                                            });
                                            queue.add(request2);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(myTag, "onErrorResponse: " + error.getMessage());
                                    }
                                });
                                queue.add(request1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            txtLocation.setText("Error");
                            Log.d(myTag, error.toString());
                            error.printStackTrace();
                        }
                    }
            );

            queue.add(request);





        txtDate.setText(new SimpleDateFormat("E, MMM dd, yyyy").format(new Date()));


    }

    private void setUpByHour() {

        hourForecast = new ArrayList<>();
        lstByHour = findViewById(R.id.lstByHour);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lstByHour.setLayoutManager(linearLayoutManager);
        lstByHour.setItemAnimator(new DefaultItemAnimator());

        byHourAdapter = new byHourAdapter(hourForecast,getApplicationContext());
        lstByHour.setAdapter(byHourAdapter);
    }

    /**
     * Set up the recycler view for the by day forecast. Initialize adapters and list.
     */
    private void setUpByDay() {

        byDayAdapter = new ByDayAdapter(byDayForecast, this);
        byDayForecast = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lstByDay.setLayoutManager(linearLayoutManager);
        lstByDay.setItemAnimator(new DefaultItemAnimator());

        byDayAdapter = new ByDayAdapter(byDayForecast,getApplicationContext());
        lstByDay.setAdapter(byDayAdapter);
    }

    /**
     * Make api call to get the by hour forecast. Add hourly forecast to the by hour list.
     * @param key The location key as a string.
     */
    private void getByHourForecast(String key) {
        JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, "https://dataservice.accuweather.com/forecasts/v1/hourly/12hour/" + key + "?apikey=VNJ7wu0YO9pEaab65xSSUjGeW2J72jnL", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DEBUG","heree");
                JSONObject jName = null;
                try {

                    for (int i = 0; i < response.length(); i++) {
                        jName = response.getJSONObject(i);
                        String imperial = jName.getJSONObject("Temperature").getString("Value");
                        long epochTime = jName.getLong("EpochDateTime");
                        int weatherIcon = jName.getInt("WeatherIcon");
                        Log.d("testing", epochTime + "");
                        hourForecast.add(new byHour(epochTime,weatherIcon,imperial));
                    }
                    byHourAdapter.notifyDataSetChanged();
                    Log.d("DEBUG",hourForecast.toString());


                    jName = response.getJSONObject(0);
                    String imperial = jName.getJSONObject("Temperature").getJSONObject("Imperial").getString("Value");
                    //need to build the byHour.xml and input the data

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(myTag, "Error: " + error.getMessage());
            }
        });
        queue.add(request3);
    }

    /**
     * Make an api call to get the by day forecast. Add days to list.
     * @param key The location key as a string.
     */
    private void getByDayForecast(String key) {
        String byDayURL = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/"+key+"?apikey=jgnJnWRQkPKBFTkFqZzI8Njy2XdovHYP";
        //THIS
        Log.d("testing", key);
        Log.d("testing", "dsfgdf");
        JsonObjectRequest request4 = new JsonObjectRequest(Request.Method.GET, byDayURL, null, response -> {
            try {
                JSONArray jArray = response.getJSONArray("DailyForecasts");


                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jName = jArray.getJSONObject(i);
                    String date = jName.getString("Date");
                    String high = jName.getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                    String low = jName.getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");
                    String phrase = jName.getJSONObject("Day").getString("IconPhrase");
                    Log.d("testing", phrase);

                    byDayForecast.add(new ByDay(date,high,low,phrase,20));
                }
                byDayAdapter.notifyDataSetChanged();




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d(myTag, error.toString()));
        Log.d("test", "onResponse: " + request4);
        queue.add(request4);
    }
}