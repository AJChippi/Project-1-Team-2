package com.example.project_1team_2.byDayDisplay;

/**
 * The type By day.
 */
public class ByDay {
    /**
     * The Day.
     */
    String day;
    /**
     * The High.
     */
    String high;
    /**
     * The Low.
     */
    String low;
    /**
     * The Phrase.
     */
    String phrase;
    /**
     * The Percent precipitation.
     */
    int percentPrecipitation;
    /**
     * The Precipitation intensity.
     */
    String PrecipitationIntensity;

    /**
     * Instantiates a new By day.
     *
     * @param day                    the day
     * @param high                   the high
     * @param low                    the low
     * @param phrase                 the phrase
     * @param percentPrecipitation   the percent precipitation
     * @param precipitationIntensity the precipitation intensity
     */
    public ByDay(String day, String high, String low, String phrase, int percentPrecipitation, String precipitationIntensity) {
        this.day = convertToDayOfWeek(day);
        this.high = high;
        this.low = low;
        this.phrase = phrase;
        this.percentPrecipitation = percentPrecipitation;
        this.PrecipitationIntensity = precipitationIntensity;
    }

    /**
     * Instantiates a new By day.
     */
    public ByDay() {
    }

    /**
     * Convert to day of week string.
     *
     * @param day the day
     * @return the string
     */
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
