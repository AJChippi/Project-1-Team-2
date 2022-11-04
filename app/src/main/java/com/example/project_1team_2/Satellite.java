package com.example.project_1team_2;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import android.os.Bundle;

public class Satellite extends AppCompatActivity {
=======
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
    final String GEO_POSITION_URL = "https://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=VNJ7wu0YO9pEaab65xSSUjGeW2J72jnL&q=43.7322%2C-83.4511";
    final String RADAR_IMAGE_URL = "https://dataservice.accuweather.com/imagery/v1/maps/radsat/480x480/338779";
    TextView txtPlaceSatellite;
    ImageView ivSatellite;
    double latitude, longitude;
    String locationKey;

    RequestQueue requestQueue;
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satellite);
<<<<<<< HEAD
=======
        //Intent intent = getIntent();
        // TPM hardcoding values for now.
        //latitude = 43.7322;
        //longitude = -83.4511;

        requestQueue = Volley.newRequestQueue(this);

        txtPlaceSatellite = findViewById(R.id.txtPlaceSatellite);
        ivSatellite = findViewById(R.id.ivSatellite);

        txtPlaceSatellite.setText("Saginaw");
        Log.d("Testing", "dsfsdf");

        setGeoPositionKey();
        setSatelliteImage();

    }

    public void setGeoPositionKey() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, GEO_POSITION_URL, null, response -> {
            try {
                locationKey = response.getString("Key");
                Log.d("Testing", locationKey);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        requestQueue.add(request);
    }

    public void setSatelliteImage() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, RADAR_IMAGE_URL, null, response -> {
            try {
                locationKey = response.getString("Key");
                Log.d("Testing", locationKey);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        requestQueue.add(request);
>>>>>>> 73e45fac8d1887ed5158177cf0fb33970e15662a
    }
}