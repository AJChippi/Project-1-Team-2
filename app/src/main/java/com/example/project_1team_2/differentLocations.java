package com.example.project_1team_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

<<<<<<< HEAD
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

=======
>>>>>>> main
public class differentLocations extends AppCompatActivity {

    RecyclerView locationList;
    ArrayList<Locations> locationsArrayList;
    String searchName = "";
    String locationURL = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=jgnJnWRQkPKBFTkFqZzI8Njy2XdovHYP&q="+(searchName.length()==0?"saginaw":searchName);
    String city;
    String localTime;
    String forcastInfo;
    String temperature;
    String tempHighest;
    String tempLowest;
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_different_locations);

        locationsArrayList = new ArrayList<>();
        locationList = findViewById(R.id.locationList);
        queue = Volley.newRequestQueue(this);


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, locationURL, null, response -> {
            JSONObject jName = null;
            try {
                jName = response.getJSONObject(0);
                String name = jName.getString("LocalizedName");
                String key = jName.getString("Key");
                String locationURL = "https://dataservice.accuweather.com/currentconditions/v1/" + key + "?apikey=jgnJnWRQkPKBFTkFqZzI8Njy2XdovHYP";
                city = name;

                JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, locationURL, null, response1 -> {
                    JSONObject jName1 = null;
                    try {
                        jName1 = response1.getJSONObject(0);
                        String imperial = jName1.getJSONObject("Temperature").getJSONObject("Imperial").getString("Value");
                        String weatherText = jName1.getString("WeatherText");
                        temperature=imperial;
                        forcastInfo=weatherText;
                        String url = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/" + key + "?apikey=jgnJnWRQkPKBFTkFqZzI8Njy2XdovHYP";
                        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null, response11 -> {
                            try {
                                String high = response11.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Maximum").getString("Value");
                                String low = response11.getJSONArray("DailyForecasts").getJSONObject(0).getJSONObject("Temperature").getJSONObject("Minimum").getString("Value");
                                tempHighest = high;
                                tempLowest = low;
                                JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, "https://dataservice.accuweather.com/forecasts/v1/hourly/12hour/" + key + "?apikey=jgnJnWRQkPKBFTkFqZzI8Njy2XdovHYP", null, response111 -> {
                                    JSONObject jName11 = null;
                                    try {
                                        jName11 = response111.getJSONObject(0);
                                        String imperial1 = jName11.getJSONObject("Temperature").getJSONObject("Imperial").getString("Value");
                                        //need to build the byHour.xml and input the data
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }, error -> {
//                                                            Log.d(myTag, "Error: " + error.getMessage());
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> {
//                                                Log.d(myTag, "Error: " + error.getMessage());
                        });
                        queue.add(request2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
//                                    Log.d(myTag, "onErrorResponse: " + error.getMessage());
                });
                queue.add(request1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
                error -> {
                    city="Error";
//                        Log.d(myTag, error.toString());
                    error.printStackTrace();
                }
        );

        queue.add(request);





        //Adding dummy data to arraylist
        locationsArrayList.add(new Locations(city, localTime,forcastInfo,tempHighest,tempLowest,temperature));
//        locationsArrayList.add(new Locations("Troy", "24:15","Cloudy",71.5,79.8,65));
//        locationsArrayList.add(new Locations("Detroit", "15:15","Mostly Clear",78.5,69.8,85));
//        locationsArrayList.add(new Locations("New York", "15:15","Mostly Clear",78.5,69.8,60));
//        locationsArrayList.add(new Locations("Tampa", "15:15","Mostly Clear",78.5,69.8,95));
//        locationsArrayList.add(new Locations("Miami", "15:15","Mostly Clear",78.5,69.8,95));
//        locationsArrayList.add(new Locations("Dhaka", "15:15","Mostly Clear",78.5,69.8,95));


        LocationAdapter adapter = new LocationAdapter(locationsArrayList,this);
        locationList.setAdapter(adapter);
        locationList.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();

        /**
         * Swipe to remove item from location list
         */
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                locationsArrayList.remove(viewHolder.getLayoutPosition());
                adapter.notifyDataSetChanged();

            }
        });
        helper.attachToRecyclerView(locationList);



    }
}