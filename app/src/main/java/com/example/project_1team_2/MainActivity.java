package com.example.project_1team_2;
import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import android.content.Intent; import android.os.Bundle; import android.util.Log;
import android.view.View; import android.widget.Button;
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
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
=======

>>>>>>> main
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
<<<<<<< HEAD
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
=======

>>>>>>> main
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageButton btnSettings, btnFavorites, btnSatellite;

    TextView txtDate, txtLocation, txtDegree, txtCondition, txtHighToLow;

    // define this somewhere else
    ListView lstByHour, lstByDay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        queue = Volley.newRequestQueue(this);
        btnSettings = findViewById(R.id.btnSettings);
        btnFavorites = findViewById(R.id.btnFavorites);
        btnSatellite = findViewById(R.id.btnSatellite);


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

        btnFavorites.setOnClickListener(view ->{
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
                                            txtDegree.setText(imperial);
                                            txtCondition.setText(weatherText);
                                            String url = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + key + "?apikey=jgnJnWRQkPKBFTkFqZzI8Njy2XdovHYP";
                                            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        String high = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                                                        String low = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");
                                                        txtHighToLow.setText(high + "°/" + low + "°");
                                                        JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, "https://dataservice.accuweather.com/forecasts/v1/hourly/12hour/" + key + "?apikey=jgnJnWRQkPKBFTkFqZzI8Njy2XdovHYP", null, new Response.Listener<JSONArray>() {
                                                            @Override
                                                            public void onResponse(JSONArray response) {
                                                                JSONObject jName = null;
                                                                try {
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