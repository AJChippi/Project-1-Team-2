package com.example.project_1team_2.byHourDisplay;

import java.text.SimpleDateFormat;
import java.util.Date;

public class byHour {
    private String time;
    private String weatherIcon;
    private String temp;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
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
        SimpleDateFormat ft =new SimpleDateFormat ("hh a");

        this.time = ft.format(d);
        if (weatherIcon<10)
            this.weatherIcon = "0"+weatherIcon;
        else
            this.weatherIcon = ""+weatherIcon;
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