package com.example.project_1team_2;

/**
 * The type Locations.
 */
public class Locations {
    /**
     * The City.
     */
    String city;
    /**
     * The Local time.
     */
    String localTime;
    /**
     * The Forecast info.
     */
    String forecastInfo;
    /**
     * The Highest temp.
     */
    String highestTemp;
    /**
     * The Lowest temp.
     */
    String lowestTemp;
    /**
     * The Temperature.
     */
    String temperature;

    /**
     * Instantiates a new Locations.
     *
     * @param city         the city
     * @param localTime    the local time
     * @param forecastInfo the forecast info
     * @param highestTemp  the highest temp
     * @param lowestTemp   the lowest temp
     * @param temperature  the temperature
     */
    public Locations(String city, String localTime, String forecastInfo, String highestTemp, String lowestTemp, String temperature) {
        this.city = city;
        this.localTime = localTime;
        this.forecastInfo = forecastInfo;
        this.highestTemp = highestTemp;
        this.lowestTemp = lowestTemp;
        this.temperature = temperature;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }
}
