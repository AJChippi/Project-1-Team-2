package com.example.project_1team_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button click;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        click = findViewById(R.id.click);

        click.setOnClickListener(view -> {
            Log.d("TAG", "onCreate: ");
        });

    //hi

    }
}