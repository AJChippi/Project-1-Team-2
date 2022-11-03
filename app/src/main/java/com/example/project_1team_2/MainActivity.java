package com.example.project_1team_2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.HorizontalScrollView;

import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response; 
import com.android.volley.VolleyError; 
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.project_1team_2.byHourDisplay.byHour;
import com.example.project_1team_2.byHourDisplay.byHourAdapter;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageButton btnSettings, btnListFavorites, btnSatellite;

    TextView txtDate, txtLocation, txtDegree, txtCondition, txtHighToLow;

    //by hour forecast list
    ArrayList<byHour> hourForecast ;
    com.example.project_1team_2.byHourDisplay.byHourAdapter byHourAdapter;
    // define this somewhere else
    ListView lstByDay;
    RecyclerView lstByHour;


    RequestQueue queue;
    String myTag = "MY_APP";

    String searchName = "";
    String locationURL = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=VNJ7wu0YO9pEaab65xSSUjGeW2J72jnL&q="+(searchName.length()==0?"saginaw":searchName);


    ArrayList<ByDay> byDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        byDay = new ArrayList<>();


        queue = Volley.newRequestQueue(this);
        btnSettings = findViewById(R.id.btnSettings);
        btnListFavorites = findViewById(R.id.btnListFavorite);
        btnSatellite = findViewById(R.id.btnSatellite);


        //By Hour Forecast
        setUpByHour();

        ByDayAdapter adapter = new ByDayAdapter(byDay);

        lstByDay = findViewById(R.id.lstByDay);
        lstByDay.setAdapter(adapter);

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
                                            String url = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + key + "?apikey=VNJ7wu0YO9pEaab65xSSUjGeW2J72jnL";
                                            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        String high = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                                                        String low = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");
                                                        txtHighToLow.setText( "High: "+high + "° - Low: " + low + "°");
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
                                                                        hourForecast.add(new byHour(epochTime,weatherIcon,imperial));
                                                                    }
                                                                    byHourAdapter.notifyDataSetChanged();
                                                                    Log.d("DEBUG",hourForecast.toString());


                                                                    jName = response.getJSONObject(0);
                                                                    String imperial = jName.getJSONObject("Temperature").getJSONObject("Imperial").getString("Value");
                                                                    //need to build the byHour.xml and input the data

                                                                    String byDayURL = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/"+key+"?apikey=jgnJnWRQkPKBFTkFqZzI8Njy2XdovHYP";
//THIS
                                                                    JsonObjectRequest request4 = new JsonObjectRequest(Request.Method.GET, byDayURL, null, new Response.Listener<JSONObject>() {
                                                                        @Override
                                                                        public void onResponse(JSONObject response) {
                                                                            try {
                                                                                JSONArray jArray = response.getJSONArray("DailyForecasts");

                                                                                    JSONObject jName = jArray.getJSONObject(0);
                                                                                    String date = jName.getString("Date");
                                                                                    String high = jName.getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                                                                                    String low = jName.getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");
                                                                                    String phase = jName.getJSONObject("Day").getString("IconPhrase");

                                                                                    byDay.add(new ByDay(date,high,low,phase,20));

                                                                                adapter.notifyDataSetChanged();
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        }
                                                                    }, new Response.ErrorListener() {
                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {
                                                                            Log.d(myTag, error.toString());
                                                                        }
                                                                    });
                                                                    Log.d("test", "onResponse: " + request4);
                                                                    queue.add(request4);
//THIS
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

    class ByDayAdapter extends BaseAdapter {

        ArrayList<ByDay> byDay;

        public ByDayAdapter(ArrayList<ByDay> byDay) {
            this.byDay = byDay;
        }

        @Override
        public int getCount() {
            return byDay.size();
        }

        @Override
        public Object getItem(int i) {
            return byDay.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null )
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.by_day, viewGroup, false);

            ByDay byDay = (ByDay) getItem(i);

            TextView txtDay = view.findViewById(R.id.txtDay);
            TextView txtProgress = view.findViewById(R.id.txtProgess);
            ProgressBar progressBarPrecipitation = view.findViewById(R.id.progressBarPrecipitation);
            TextView txtPhrase = view.findViewById(R.id.txtPhrase);
            TextView txtHighLow = view.findViewById(R.id.txtHighLow);

            txtDay.setText(byDay.day);
            progressBarPrecipitation.setProgress(byDay.percentPrecipitation);
            txtPhrase.setText(byDay.phase);
            int progress = byDay.percentPrecipitation;
            txtProgress.setText(progress + "%");
            txtHighLow.setText(byDay.high + "°/" + byDay.low + "°");
            return view;
        }

    }
}