package com.example.project_1team_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageButton settings;
    TextView txtDate, txtLocation, txtDegree, txtCondition, txtHighToLow;

    // define this somewhere else
    ListView lstByHour, lstByDay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = findViewById(R.id.btnListFavorite);
        txtDate = findViewById(R.id.txtDate);
        txtLocation = findViewById(R.id.txtLocation);
        txtDegree = findViewById(R.id.txtDegree);
        txtCondition = findViewById(R.id.txtCondition);
        txtHighToLow = findViewById(R.id.txtHighToLow);

        txtDate.setText(new SimpleDateFormat("E, MMM dd, yyyy").format(new Date()));


    }
}