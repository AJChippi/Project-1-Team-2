package com.example.project_1team_2;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.project_1team_2.R;

    public class Setting {
        private String unit;
        private boolean currentLocationUse;
        private boolean refByHour;
        private boolean refByDay;

        public Setting(String unit, boolean currentLocationUse, boolean refByHour, boolean refByDay) {
            this.unit = unit;
            this.currentLocationUse = currentLocationUse;
            this.refByHour = refByHour;
            this.refByDay = refByDay;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public boolean isCurrentLocationUse() {
            return currentLocationUse;
        }

        public void setCurrentLocationUse(boolean currentLocationUse) {
            this.currentLocationUse = currentLocationUse;
        }

        public boolean isRefByHour() {
            return refByHour;
        }

        public void setRefByHour(boolean refByHour) {
            this.refByHour = refByHour;
        }

        public boolean isRefByDay() {
            return refByDay;
        }

        public void setRefByDay(boolean refByDay) {
            this.refByDay = refByDay;
        }

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

