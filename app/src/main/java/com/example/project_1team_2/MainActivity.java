package com.example.project_1team_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ImageButton settings;
    TextView txtDate, txtLocation, txtDegree, txtCondition, txtHighToLow;

    // define this somewhere else
    ListView lstByHour, lstByDay;

    RequestQueue queue;
    String locationURL = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=LjSUBb6fLYP2aN2OJG4TaPJXYxEmiQD2&q=Saginaw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        settings = findViewById(R.id.settings);
        txtDate = findViewById(R.id.txtDate);
        txtLocation = findViewById(R.id.txtLocation);
        txtDegree = findViewById(R.id.txtDegree);
        txtCondition = findViewById(R.id.txtCondition);
        txtHighToLow = findViewById(R.id.txtHighToLow);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                locationURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray locationArray = new JSONArray(response);
                            String location = locationArray.getJSONObject(0).getString("LocalizedName");
                            Log.d("location", location);
                                txtLocation.setText(location);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        queue.add(request);


    }
}