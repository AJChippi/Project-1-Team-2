package com.example.project_1team_2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.example.project_1team_2.models.Setting;
import com.google.android.material.button.MaterialButtonToggleGroup;
import java.util.Objects;

public class Settings extends AppCompatActivity {
    private static final String TAG = "Settings";

    ActionBar supportActionBar;
    Button backBtn;
    MaterialButtonToggleGroup unitToggleGroup;
    CheckBox refByHourCheckbox;
    CheckBox refByDayCheckbox;

    SharedPreferences settingsPref;
    SharedPreferences.Editor settingsEditor;
    MaterialButtonToggleGroup.OnButtonCheckedListener unitToggleCheckListener;

    CompoundButton.OnCheckedChangeListener refByHourChangeListener;
    CompoundButton.OnCheckedChangeListener refByDayChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsPref = this.getSharedPreferences(
                this.getResources().getString(R.string.settings_preferences_file_key), Context.MODE_PRIVATE);
        settingsEditor = settingsPref.edit();

        supportActionBar = getSupportActionBar();
        if(supportActionBar != null) {
            supportActionBar.setTitle("Settings");
        }

        initWidgets();
        initSettings();
        setOnClickListeners();
        initCheckListeners();

    }

    /**
     * Add listeners to the toggle buttons
     */
    private void initCheckListeners() {
        unitToggleCheckListener = (group, checkedId, isChecked) -> {
            String checkedOption;

            if(isChecked) {
                if(checkedId == R.id.celsiusButton) {
                    checkedOption = "C";
                }
                else {
                    checkedOption = "F";
                }
                settingsEditor.putString(getResources().getString(R.string.settings_unit_key), checkedOption);
                settingsEditor.apply();
            }

        };

        unitToggleGroup.addOnButtonCheckedListener(unitToggleCheckListener);

        refByHourChangeListener = (compoundButton, checked) -> {
            settingsEditor.putBoolean(getResources().getString(R.string.settings_reference_by_hour_key), checked);
            settingsEditor.apply();
        };
        refByHourCheckbox.setOnCheckedChangeListener(refByHourChangeListener);

        refByDayChangeListener = (compoundButton, checked) -> {
            settingsEditor.putBoolean(getResources().getString(R.string.settings_reference_by_day_key), checked);
            settingsEditor.apply();
        };
        refByDayCheckbox.setOnCheckedChangeListener(refByDayChangeListener);
    }

    /**
     * Initialize the settings from the shared preferences
     */
    private void initWidgets() {
        backBtn = findViewById(R.id.backBtn);
        unitToggleGroup = findViewById(R.id.unitToggleButton);
        refByHourCheckbox = findViewById(R.id.byHourReferenceCheckbox);
        refByDayCheckbox = findViewById(R.id.byDayReferenceCheckbox);

        String getUnitPreference = settingsPref.getString(getResources().getString(R.string.settings_unit_key),"F");
        switch (getUnitPreference){
            case("F"):
                unitToggleGroup.clearChecked();
                break;
            case "C":

        }

        boolean getHourPreference = settingsPref.getBoolean(getResources().getString(R.string.settings_reference_by_hour_key),true);
        refByHourCheckbox.setChecked(getHourPreference);

        boolean getDayPreference = settingsPref.getBoolean(getResources().getString(R.string.settings_reference_by_day_key),true);
        refByDayCheckbox.setChecked(getDayPreference);
    }

    private void setOnClickListeners() {
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void initSettings() {
        Setting settings = Setting.initSettings(this);
        int toggleBtnIdToCheck = R.id.celsiusButton;
        if(!Objects.equals(settings.getUnit(), "C")) {
            toggleBtnIdToCheck = R.id.fahrenheitButton;
        }

        unitToggleGroup.check(toggleBtnIdToCheck);
        refByHourCheckbox.setChecked(settings.isRefByHour());
        refByDayCheckbox.setChecked(settings.isRefByDay());
    }
}