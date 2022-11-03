package com.example.project_1team_2.byDayDisplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ByDay {
    String day;
    String high;
    String low;
    String phase;
    int percentPrecipitation;

    public ByDay(String day, String high, String low, String phase, int percentPrecipitation) {
        this.day = day;
        this.high = high;
        this.low = low;
        this.phase = phase;
        this.percentPrecipitation = percentPrecipitation;
    }

    public ByDay() {
    }

    @Override
    public String toString() {
        return "ByDay{" +
                ", day='" + day + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", phase='" + phase + '\'' +
                ", percentPrecipitation='" + percentPrecipitation + '\'' +
                '}';
    }
}
