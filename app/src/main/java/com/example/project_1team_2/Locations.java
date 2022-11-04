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

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public String getForecastInfo() {
        return forecastInfo;
    }

    public void setForecastInfo(String forecastInfo) {
        this.forecastInfo = forecastInfo;
    }

    public String getHighestTemp() {
        return highestTemp;
    }

    public void setHighestTemp(String highestTemp) {
        this.highestTemp = highestTemp;
    }

    public String getLowestTemp() {
        return lowestTemp;
    }

    public void setLowestTemp(String lowestTemp) {
        this.lowestTemp = lowestTemp;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
