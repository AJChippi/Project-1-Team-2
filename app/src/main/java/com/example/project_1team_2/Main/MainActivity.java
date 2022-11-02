package com.example.project_1team_2.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_1team_2.R;
import com.example.project_1team_2.Setting.settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    final String TAG = "WeatherDebug";
    ImageButton btnSettings;
    TextView txtDate, txtLocation, txtDegree, txtCondition, txtHighToLow;
    Button settings2;

    //by hour forecast list
    ArrayList<byHour> hourForecast ;
    byHourAdapter byHourAdapter;

    // define this somewhere else
    ListView lstByHour, lstByDay;
    String myTag = "MY_APP";

    String searchName = "";

    RequestQueue queue;
    // generate locationURl from search name if searchName is empty default to saginaw

    String locationURL = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=jgnJnWRQkPKBFTkFqZzI8Njy2XdovHYP&q=" + (searchName.length() == 0 ? "saginaw" : searchName);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        queue = Volley.newRequestQueue(this);
        btnSettings = findViewById(R.id.btnSettings);

        txtDate = findViewById(R.id.txtDate);
        txtLocation = findViewById(R.id.txtLocation);
        txtDegree = findViewById(R.id.txtDegree);
        txtCondition = findViewById(R.id.txtCondition);
        txtHighToLow = findViewById(R.id.txtHighToLow);

        //By Hour Forecast
        hourForecast = new ArrayList<>();
        lstByHour = findViewById(R.id.lstByHour);
        byHourAdapter = new byHourAdapter(hourForecast,getApplicationContext());
        lstByHour.setAdapter(byHourAdapter);

        btnSettings.setOnClickListener(view -> {
            // go to settings page
            Intent intent = new Intent(MainActivity.this, settings.class);
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
                            String locationURL = "https://dataservice.accuweather.com/currentconditions/v1/" + key + "?apikey=jgnJnWRQkPKBFTkFqZzI8Njy2XdovHYP";
                            txtLocation.setText(name);

                            JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, locationURL, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    JSONObject jName = null;
                                    try {
                                        jName = response.getJSONObject(0);
                                        String imperial = jName.getJSONObject("Temperature").getJSONObject("Imperial").getString("Value");
                                        String weatherText = jName.getString("WeatherText");
                                        txtDegree.setText(Double.valueOf(imperial).intValue() + "°");
                                        txtCondition.setText(weatherText);
                                        String url = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + key + "?apikey=jgnJnWRQkPKBFTkFqZzI8Njy2XdovHYP";
                                        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    String high = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                                                    String low = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");
                                                    txtHighToLow.setText("High: " + high + "° - Low: " + low + "°");
                                                    JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, "https://dataservice.accuweather.com/forecasts/v1/hourly/12hour/" + key + "?apikey=jgnJnWRQkPKBFTkFqZzI8Njy2XdovHYP", null, new Response.Listener<JSONArray>() {
                                                        @Override
                                                        public void onResponse(JSONArray response) {
                                                            JSONObject jName = null;

                                                            try {
                                                                for (int i = 0; i < response.length(); i++) {
                                                                    jName = response.getJSONObject(i);
                                                                    String imperial = jName.getJSONObject("Temperature").getString("Value");
                                                                    long epochTime = jName.getLong("EpochDateTime");
                                                                    int weatherIcon = jName.getInt("WeatherIcon");
                                                                    hourForecast.add(new byHour(epochTime,weatherIcon,imperial));
                                                                }
                                                               byHourAdapter.notifyDataSetChanged();

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
}