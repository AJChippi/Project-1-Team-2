package com.example.project_1team_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    ImageButton btnSettings, btnListFavorites, btnSatellite;

    TextView txtDate, txtLocation, txtDegree, txtCondition, txtHighToLow;

    final String API_KEY = "0gBx63kUUsZowQpDmsk1EGK0MphE8ELc";
    //by hour forecast list
    ArrayList<byHour> hourForecast;
    ArrayList<ByDay> byDayForecast;
    byHourAdapter byHourAdapter;
    ByDayAdapter byDayAdapter;
    // define this somewhere else
    RecyclerView lstByDay;
    RecyclerView lstByHour;

    GoogleMap googleMap;

    SupportMapFragment mapFragment;
    RequestQueue queue;
    String myTag = "MY_APP";
    double latitude;
    double longitude;

    String searchName = "";
    String locationURL = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey="+ API_KEY + "&q="+(searchName.length()==0?"saginaw":searchName);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        btnSettings = findViewById(R.id.btnSettings);
        btnListFavorites = findViewById(R.id.btnListFavorite);
        lstByDay = findViewById(R.id.lstByDay);
        txtDate = findViewById(R.id.txtDate);
        txtLocation = findViewById(R.id.txtLocation);
        txtDegree = findViewById(R.id.txtDegree);
        txtCondition = findViewById(R.id.txtCondition);
        txtHighToLow = findViewById(R.id.txtHighToLow);

        //By Hour Forecast
        setUpByHour();

        // By day forecast
        setUpByDay();


        btnSettings.setOnClickListener(view ->{
            // go to settings page
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);

        });

        btnListFavorites.setOnClickListener(view ->{
            // go to favorites page
            Intent intent = new Intent(MainActivity.this, differentLocations.class);
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
                                latitude = jName.getJSONObject("GeoPosition").getDouble("Latitude");
                                longitude = jName.getJSONObject("GeoPosition").getDouble("Longitude");

                                // Set map location.
                                mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                        .findFragmentById(R.id.map);
                                mapFragment.getMapAsync(MainActivity.this);

                                String key = jName.getString("Key");
                                String locationURL = "https://dataservice.accuweather.com/currentconditions/v1/" + key + "?apikey=" + API_KEY;
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
                                            String url = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + key + "?apikey=" + API_KEY;
                                            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        String high = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                                                        String low = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");
                                                        txtHighToLow.setText( "High: "+high + "° - Low: " + low + "°");
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
                                            getByHourForecast(key);
                                            getByDayForecast(key);
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

    /**
     * Set up by hour view for by hour forecast. Initialize adapters and list.
     */
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
        JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, "https://dataservice.accuweather.com/forecasts/v1/hourly/12hour/" + key + "?apikey=" + API_KEY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jName = null;
                try {

                    // Loop through and build by hour list.
                    for (int i = 0; i < response.length(); i++) {
                        jName = response.getJSONObject(i);
                        String imperial = jName.getJSONObject("Temperature").getString("Value");
                        long epochTime = jName.getLong("EpochDateTime");
                        int weatherIcon = jName.getInt("WeatherIcon");
                        hourForecast.add(new byHour(epochTime,weatherIcon,imperial));
                    }
                    byHourAdapter.notifyDataSetChanged();

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
        String byDayURL = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/"+key+"?apikey=" + API_KEY;
        //THIS
        JsonObjectRequest request4 = new JsonObjectRequest(Request.Method.GET, byDayURL, null, response -> {
            try {
                JSONArray jArray = response.getJSONArray("DailyForecasts");
                JSONObject headline = response.getJSONObject("Headline");
                String headlineText = headline.getString("Text");

                // Loop through and create for day list.
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jName = jArray.getJSONObject(i);
                    String date = jName.getString("Date");
                    String high = jName.getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                    String low = jName.getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");
                    String phrase = jName.getJSONObject("Day").getString("IconPhrase");
                    Boolean hasPrecipitation = jName.getJSONObject("Day").getBoolean("HasPrecipitation");

                    String precipitationProbability = "";
                    if(hasPrecipitation){
                         precipitationProbability = jName.getJSONObject("Day").getString("PrecipitationIntensity");
                    }

                    int intensity = 0;
                    switch(precipitationProbability){
                        case "Light":
                            precipitationProbability = "Light Rain";
                             intensity = 33;
                            break;
                        case "Moderate":
                            precipitationProbability = "Moderate Rain";
                             intensity = 66;
                            break;
                        case "Heavy":
                            precipitationProbability = "Heavy Rain";
                             intensity = 100;
                            break;
                        default:
                            precipitationProbability = "No Rain";
                             intensity = 0;
                            break;
                    }

                    byDayForecast.add(new ByDay(date,high,low,phrase,intensity,precipitationProbability));
                }
                byDayAdapter.notifyDataSetChanged();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("HEADLINE");
                builder.setMessage(headlineText);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d(myTag, error.toString()));
        queue.add(request4);
    }

    /**
     * Use latitude and longitude from api call to set map location.
     * @param googleMap Map object.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng location = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Marker"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        googleMap.getUiSettings().setAllGesturesEnabled(false);

        // Pass latitude and longitude and route to map activity on click.
        googleMap.setOnMapClickListener(latLng -> {
          Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);
        });
    }
}