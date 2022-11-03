package com.example.project_1team_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Satellite extends AppCompatActivity {
    final String GEO_POSITION_URL = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=VNJ7wu0YO9pEaab65xSSUjGeW2J72jnL&q=43.7322%2C-83.4511";
    TextView txtPlaceSatellite;
    ImageView ivSatellite;
    double latitude, longitude;
    int GPSKey;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satellite);
        //Intent intent = getIntent();
        // TPM hardcoding values for now.
        //latitude = 43.7322;
        //longitude = -83.4511;

        requestQueue = Volley.newRequestQueue(this);

        txtPlaceSatellite = findViewById(R.id.txtPlaceSatellite);
        ivSatellite = findViewById(R.id.ivSatellite);

        txtPlaceSatellite.setText("Saginaw");
        Log.d("Testing", "dsfsdf");

        fetchData();

    }

    public void fetchData() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, GEO_POSITION_URL, null, response -> {
            try {
                Log.d("Testing", "something");
                String result = response.getString("key");
                Log.d("Testing", result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        requestQueue.add(request);
    }
}