package com.example.project_1team_2;

import java.util.Objects;

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
