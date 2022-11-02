package com.example.project_1team_2;

public class Locations {
    String city;
    String localTime;
    String forecastInfo;
    double highestTemp;
    double lowestTemp;
    double temperature;

    public Locations(String city, String localTime, String forecastInfo, double highestTemp, double lowestTemp, double temperature) {
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

    public double getHighestTemp() {
        return highestTemp;
    }

    public void setHighestTemp(double highestTemp) {
        this.highestTemp = highestTemp;
    }

    public double getLowestTemp() {
        return lowestTemp;
    }

    public void setLowestTemp(double lowestTemp) {
        this.lowestTemp = lowestTemp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
