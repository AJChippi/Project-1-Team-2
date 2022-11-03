package com.example.project_1team_2.byDayDisplay;

public class ByDay {
    String day;
    String high;
    String low;
    String phrase;
    int percentPrecipitation;
    String PrecipitationIntensity;

    public ByDay(String day, String high, String low, String phrase, int percentPrecipitation, String precipitationIntensity) {
        this.day = convertToDayOfWeek(day);
        this.high = high;
        this.low = low;
        this.phrase = phrase;
        this.percentPrecipitation = percentPrecipitation;
        this.PrecipitationIntensity = precipitationIntensity;
    }

    public ByDay() {
    }

    public String convertToDayOfWeek(String day) {
        int indexOfT = day.indexOf("T");
        int indexOfDash = day.indexOf("-");
        return day.substring(indexOfDash + 1, indexOfT);
    }

    @Override
    public String toString() {
        return "ByDay{" +
                ", day='" + day + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", phrase='" + phrase + '\'' +
                ", percentPrecipitation='" + percentPrecipitation + '\'' +
                ", PrecipitationIntensity='" + PrecipitationIntensity + '\'' +
                '}';
    }
}
