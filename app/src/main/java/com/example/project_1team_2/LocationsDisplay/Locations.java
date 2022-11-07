package com.example.project_1team_2.LocationsDisplay;

public class Locations {
    String city;
    String localTime;
    String countryName;
    String forecastInfo;
    String highestTemp;
    String lowestTemp;
    String temperature;

    public Locations(String city, String localTime, String countryName, String forecastInfo, String highestTemp, String lowestTemp, String temperature) {
        this.city = city;
        this.localTime = localTime;
        this.countryName = countryName;
        this.forecastInfo = forecastInfo;
        this.highestTemp = highestTemp;
        this.lowestTemp = lowestTemp;
        this.temperature = temperature;
    }
}
