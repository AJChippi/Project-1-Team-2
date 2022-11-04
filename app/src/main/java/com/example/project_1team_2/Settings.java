package com.example.project_1team_2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

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
    SwitchCompat currentLocationUseSwitch;

    SharedPreferences settingsPref;
    SharedPreferences.Editor settingsEditor;
    MaterialButtonToggleGroup.OnButtonCheckedListener unitToggleCheckListener;
    CompoundButton.OnCheckedChangeListener currLocSettingChangeListener;
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

        currLocSettingChangeListener = (compoundButton, checked) -> {
            settingsEditor.putBoolean(getResources().getString(R.string.settings_current_location_key), checked);
            settingsEditor.apply();
        };
        currentLocationUseSwitch.setOnCheckedChangeListener(currLocSettingChangeListener);

        refByHourChangeListener = (compoundButton, checked) -> {
            if(checked) {
                refByDayCheckbox.setChecked(false);
            }
            settingsEditor.putBoolean(getResources().getString(R.string.settings_reference_by_hour_key), checked);
            settingsEditor.apply();
        };
        refByHourCheckbox.setOnCheckedChangeListener(refByHourChangeListener);

        refByDayChangeListener = (compoundButton, checked) -> {
            if(checked) {
                refByHourCheckbox.setChecked(false);
            }
            settingsEditor.putBoolean(getResources().getString(R.string.settings_reference_by_day_key), checked);
            settingsEditor.apply();
        };
        refByDayCheckbox.setOnCheckedChangeListener(refByDayChangeListener);
    }

    private void initWidgets() {
        backBtn = findViewById(R.id.backBtn);
        unitToggleGroup = findViewById(R.id.unitToggleButton);
        currentLocationUseSwitch = findViewById(R.id.useCurrentLocationSwitch);
        refByHourCheckbox = findViewById(R.id.byHourReferenceCheckbox);
        refByDayCheckbox = findViewById(R.id.byDayReferenceCheckbox);
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
        currentLocationUseSwitch.setChecked(settings.isCurrentLocationUse());
        refByHourCheckbox.setChecked(settings.isRefByHour());
        refByDayCheckbox.setChecked(settings.isRefByDay());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unitToggleCheckListener != null) {
            unitToggleGroup.removeOnButtonCheckedListener(unitToggleCheckListener);
            unitToggleCheckListener = null;
        }

        currentLocationUseSwitch.setOnCheckedChangeListener(null);
        refByHourCheckbox.setOnCheckedChangeListener(null);
        refByDayCheckbox.setOnCheckedChangeListener(null);
    }
}