package com.example.project_1team_2.Main;

import java.text.SimpleDateFormat;
import java.util.Date;

public class byHour {
    private String time;
    private int weatherIcon;
    private String temp;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(int weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public byHour(long time, int weatherIcon, String temp) {
        Date d = new Date( time * 1000 );
        SimpleDateFormat ft =new SimpleDateFormat ("hh");

        this.time = ft.format(d);
        this.weatherIcon = weatherIcon;
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "byHour{" +
                "time='" + time + '\'' +
                ", weatherIcon='" + weatherIcon + '\'' +
                ", temp='" + temp + '\'' +
                '}';
    }
}
