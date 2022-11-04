package com.example.project_1team_2;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
=======
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.ImageButton;
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response; 
import com.android.volley.VolleyError; 
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

<<<<<<< HEAD
=======
import com.example.project_1team_2.byDayDisplay.ByDay;
import com.example.project_1team_2.byDayDisplay.ByDayAdapter;
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a
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

<<<<<<< HEAD
    //by hour forecast list
    ArrayList<byHour> hourForecast ;
    com.example.project_1team_2.byHourDisplay.byHourAdapter byHourAdapter;
    // define this somewhere else
    ListView lstByDay;
=======
    final String API_KEY = "jstCxB26UKe3P2NzSZGan5ZqEUnQZU64";
    //by hour forecast list
    ArrayList<byHour> hourForecast;
    ArrayList<ByDay> byDayForecast;
    byHourAdapter byHourAdapter;
    ByDayAdapter byDayAdapter;
    // define this somewhere else
    RecyclerView lstByDay;
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a
    RecyclerView lstByHour;


    RequestQueue queue;
    String myTag = "MY_APP";
<<<<<<< HEAD

    String searchName = "";
    String locationURL = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=VNJ7wu0YO9pEaab65xSSUjGeW2J72jnL&q="+(searchName.length()==0?"saginaw":searchName);


    ArrayList<ByDay> byDay;
=======
    double latitude;
    double longitude;

    String searchName = "";
    String locationURL = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey="+ API_KEY + "&q="+(searchName.length()==0?"saginaw":searchName);
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


<<<<<<< HEAD
        byDay = new ArrayList<>();


=======
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a
        queue = Volley.newRequestQueue(this);
        btnSettings = findViewById(R.id.btnSettings);
        btnListFavorites = findViewById(R.id.btnListFavorite);
        btnSatellite = findViewById(R.id.btnSatellite);
<<<<<<< HEAD
=======
        lstByDay = findViewById(R.id.lstByDay);
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a


        //By Hour Forecast
        setUpByHour();

<<<<<<< HEAD
        ByDayAdapter adapter = new ByDayAdapter(byDay);

        lstByDay = findViewById(R.id.lstByDay);
        lstByDay.setAdapter(adapter);

//        for (int i=0;i<20;i++){
//            byDay.add(new ByDay("Yesterday","60","30",R.drawable.satellite,20));
//        }

//        adapter.notifyDataSetChanged();
=======
        // By day forecast
        setUpByDay();
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a

        txtDate = findViewById(R.id.txtDate);
        txtLocation = findViewById(R.id.txtLocation);
        txtDegree = findViewById(R.id.txtDegree);
        txtCondition = findViewById(R.id.txtCondition);
        txtHighToLow = findViewById(R.id.txtHighToLow);

<<<<<<< HEAD

        btnSettings.setOnClickListener(view ->{
            // go to settings page
            Intent intent = new Intent(MainActivity.this, Settings.class);
=======
        btnSettings.setOnClickListener(view ->{
            // go to settings page
            Intent intent = new Intent(MainActivity.this, settings.class);
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a
            startActivity(intent);

        });

        btnListFavorites.setOnClickListener(view ->{
            // go to favorites page
            Intent intent = new Intent(MainActivity.this, differentLocations.class);
            startActivity(intent);

        });

<<<<<<< HEAD
        btnSatellite.setOnClickListener(view ->{
            // go to satellite page
            Intent intent = new Intent(MainActivity.this, Satellite.class);
=======
        // Pass latitude and longitude and load map.
        btnSatellite.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a
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
<<<<<<< HEAD
                                String key = jName.getString("Key");
                                String locationURL = "https://dataservice.accuweather.com/currentconditions/v1/" + key + "?apikey=VNJ7wu0YO9pEaab65xSSUjGeW2J72jnL";
=======
                                latitude = jName.getJSONObject("GeoPosition").getDouble("Latitude");
                                longitude = jName.getJSONObject("GeoPosition").getDouble("Longitude");
                                String key = jName.getString("Key");
                                String locationURL = "https://dataservice.accuweather.com/currentconditions/v1/" + key + "?apikey=" + API_KEY;
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a
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
<<<<<<< HEAD
                                            String url = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + key + "?apikey=VNJ7wu0YO9pEaab65xSSUjGeW2J72jnL";
=======
                                            Log.d("testing", weatherText);
                                            String url = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + key + "?apikey=" + API_KEY;
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a
                                            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        String high = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                                                        String low = response.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");
                                                        txtHighToLow.setText( "High: "+high + "° - Low: " + low + "°");
<<<<<<< HEAD
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
=======
                                                        Log.d("testing", low);
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a
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
<<<<<<< HEAD
=======
                                            getByHourForecast(key);
                                            getByDayForecast(key);
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a
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
<<<<<<< HEAD
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

=======
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
                Log.d("DEBUG","heree");
                JSONObject jName = null;
                try {

                    // Loop through and build by hour list.
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
        Log.d("test", "onResponse: " + request4);
        queue.add(request4);
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a
    }
}