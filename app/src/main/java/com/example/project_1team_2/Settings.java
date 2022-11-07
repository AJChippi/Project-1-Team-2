package com.example.project_1team_2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.project_1team_2.models.Setting;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.Objects;

/**
 * The type Settings.
 */
public class Settings extends AppCompatActivity {
    private static final String TAG = "Settings";

    /**
     * The Support action bar.
     */
    ActionBar supportActionBar;
    /**
     * The Back btn.
     */
    Button backBtn;
    /**
     * The Unit toggle group.
     */
    MaterialButtonToggleGroup unitToggleGroup;
    /**
     * The Ref by hour checkbox.
     */
    CheckBox refByHourCheckbox;
    /**
     * The Ref by day checkbox.
     */
    CheckBox refByDayCheckbox;

    /**
     * The Settings pref.
     */
    SharedPreferences settingsPref;
    /**
     * The Settings editor.
     */
    SharedPreferences.Editor settingsEditor;
    /**
     * The Unit toggle check listener.
     */
    MaterialButtonToggleGroup.OnButtonCheckedListener unitToggleCheckListener;

    /**
     * The Ref by hour change listener.
     */
    CompoundButton.OnCheckedChangeListener refByHourChangeListener;
    /**
     * The Ref by day change listener.
     */
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

    private void initCheckListeners() {
        unitToggleCheckListener = (group, checkedId, isChecked) -> {
            String checkedOption;
            Log.d(TAG, "initCheckListeners: id" + checkedId + "; " + R.id.celsiusButton + "; " + R.id.fahrenheitButton);

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

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(unitToggleCheckListener != null) {
//            unitToggleGroup.removeOnButtonCheckedListener(unitToggleCheckListener);
//            unitToggleCheckListener = null;
//        }
//        refByHourCheckbox.setOnCheckedChangeListener(null);
//        refByDayCheckbox.setOnCheckedChangeListener(null);
//    }
}