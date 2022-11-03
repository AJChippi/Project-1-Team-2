package com.example.project_1team_2.byDayDisplay;

public class ByDay {
    String day;
    String high;
    String low;
    String phrase;
    int percentPrecipitation;

    public ByDay(String day, String high, String low, String phrase, int percentPrecipitation) {
        this.day = convertToDayOfWeek(day);
        this.high = high;
        this.low = low;
        this.phrase = phrase;
        this.percentPrecipitation = percentPrecipitation;
    }

    public ByDay() {
    }

    public String convertToDayOfWeek(String day) {
        int indexOfT = day.indexOf("T");
        return day.substring(0, indexOfT);
    }

    @Override
    public String toString() {
        return "ByDay{" +
                ", day='" + day + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", phase='" + phrase + '\'' +
                ", percentPrecipitation='" + percentPrecipitation + '\'' +
                '}';
    }
}
