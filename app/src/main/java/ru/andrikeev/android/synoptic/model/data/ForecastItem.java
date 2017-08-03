package ru.andrikeev.android.synoptic.model.data;

import android.support.annotation.NonNull;

/**
 * Created by overtired on 01.08.17.
 */

public class ForecastItem {
    private int weatherIconId;
    private String date;
    private String description;
    private String clouds;
    private String windSpeed;
    private String temperature;
    private String pressure;
    private String humidity;
    private String temperatureUnits;

    private int windDirectionIconId;

    public ForecastItem(
            int weatherIconId,
            @NonNull String date,
            @NonNull String description,
            @NonNull String clouds,
            @NonNull String windSpeed,
            @NonNull String temperature,
            @NonNull String temperatureUnits,
            @NonNull String pressure,
            @NonNull String humidity,
            int windDirectionIconId){
        this.weatherIconId = weatherIconId;
        this.date = date;
        this.description = description;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.temperature = temperature;
        this.temperatureUnits = temperatureUnits;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windDirectionIconId = windDirectionIconId;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getClouds() {
        return clouds;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public int getWeatherIconId() {
        return weatherIconId;
    }
}
