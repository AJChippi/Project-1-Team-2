package com.example.project_1team_2.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.project_1team_2.R;

/**
 * The type Setting.
 */
public class Setting {
    private String unit;
    private boolean currentLocationUse;
    private boolean refByHour;
    private boolean refByDay;

    /**
     * Instantiates a new Setting.
     *
     * @param unit               the unit
     * @param currentLocationUse the current location use
     * @param refByHour          the ref by hour
     * @param refByDay           the ref by day
     */
    public Setting(String unit, boolean currentLocationUse, boolean refByHour, boolean refByDay) {
        this.unit = unit;
        this.currentLocationUse = currentLocationUse;
        this.refByHour = refByHour;
        this.refByDay = refByDay;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets unit.
     *
     * @param unit the unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Is current location use boolean.
     *
     * @return the boolean
     */
    public boolean isCurrentLocationUse() {
        return currentLocationUse;
    }

    /**
     * Sets current location use.
     *
     * @param currentLocationUse the current location use
     */
    public void setCurrentLocationUse(boolean currentLocationUse) {
        this.currentLocationUse = currentLocationUse;
    }

    /**
     * Is ref by hour boolean.
     *
     * @return the boolean
     */
    public boolean isRefByHour() {
        return refByHour;
    }

    /**
     * Sets ref by hour.
     *
     * @param refByHour the ref by hour
     */
    public void setRefByHour(boolean refByHour) {
        this.refByHour = refByHour;
    }

    /**
     * Is ref by day boolean.
     *
     * @return the boolean
     */
    public boolean isRefByDay() {
        return refByDay;
    }

    /**
     * Sets ref by day.
     *
     * @param refByDay the ref by day
     */
    public void setRefByDay(boolean refByDay) {
        this.refByDay = refByDay;
    }

    /**
     * Init settings setting.
     *
     * @param ctx the ctx
     * @return the setting
     */
    static public Setting initSettings(Context ctx) {
        SharedPreferences settingsPref = ctx.getSharedPreferences(
                ctx.getResources().getString(R.string.settings_preferences_file_key), Context.MODE_PRIVATE);
        String unit = settingsPref.getString(ctx.getResources().getString(R.string.settings_unit_key), "C");
        boolean currentLoc = settingsPref.getBoolean(ctx.getResources().getString(R.string.settings_current_location_key), true);
        boolean refByHour = settingsPref.getBoolean(ctx.getResources().getString(R.string.settings_reference_by_hour_key), true);
        boolean refByDay = settingsPref.getBoolean(ctx.getResources().getString(R.string.settings_reference_by_day_key), false);
        return new Setting(unit, currentLoc, refByHour, refByDay);
    }
}
