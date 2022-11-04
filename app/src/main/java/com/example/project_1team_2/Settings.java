package com.example.project_1team_2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    ActionBar supportActionBar;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backBtn = findViewById(R.id.backBtn);
        supportActionBar = getSupportActionBar();
        if(supportActionBar != null) {
            supportActionBar.setTitle("Settings");
        }

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, MainActivity.class);
            startActivity(intent);
        });

    }
}