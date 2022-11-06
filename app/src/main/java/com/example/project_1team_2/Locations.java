package com.example.project_1team_2;

public class Locations {
    String city;
    String localTime;
    String forecastInfo;
    String highestTemp;
    String lowestTemp;
    String temperature;

    public Locations(String city, String localTime, String forecastInfo, String highestTemp, String lowestTemp, String temperature) {
        this.city = city;
        this.localTime = localTime;
        this.forecastInfo = forecastInfo;
        this.highestTemp = highestTemp;
        this.lowestTemp = lowestTemp;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
