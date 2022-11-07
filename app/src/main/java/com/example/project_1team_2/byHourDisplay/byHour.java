package com.example.project_1team_2.byHourDisplay;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type By hour.
 */
public class byHour {
    private String time;
    private String weatherIcon;
    private String temp;

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Gets weather icon.
     *
     * @return the weather icon
     */
    public String getWeatherIcon() {
        return weatherIcon;
    }

    /**
     * Sets weather icon.
     *
     * @param weatherIcon the weather icon
     */
    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    /**
     * Gets temp.
     *
     * @return the temp
     */
    public String getTemp() {
        return temp;
    }

    /**
     * Sets temp.
     *
     * @param temp the temp
     */
    public void setTemp(String temp) {
        this.temp = temp;
    }

    /**
     * Instantiates a new By hour.
     *
     * @param time        the time
     * @param weatherIcon the weather icon
     * @param temp        the temp
     */
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