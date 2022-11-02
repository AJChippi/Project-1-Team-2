package com.example.project_1team_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton settings,settings2;
    TextView txtDate, txtLocation, txtDegree, txtCondition, txtHighToLow;

    // define this somewhere else
    ListView lstByHour, lstByDay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = findViewById(R.id.settings);
        settings2 = findViewById(R.id.settings2);

        txtDate = findViewById(R.id.txtDate);
        txtLocation = findViewById(R.id.txtLocation);
        txtDegree = findViewById(R.id.txtDegree);
        txtCondition = findViewById(R.id.txtCondition);
        txtHighToLow = findViewById(R.id.txtHighToLow);


        settings2.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), differentLocations.class);
            startActivity(activity2Intent);
        });


    }
}