package ru.andrikeev.android.synoptic.model.persistence;

import android.support.annotation.NonNull;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Table;

/**
 * Created by overtired on 01.08.17.
 */
//@Entity
//@Table(name = "forecast")
public class Forecast {
    //@Key @Generated
    protected int id;

    protected int weatherIconId;

    protected float message;

    protected String cityName;

    protected int cityId;

    protected long date;

    protected String description;

    protected float clouds;

    protected float windSpeed;

    protected float windDegree;

    protected float temperature;

    protected float pressure;

    protected float humidity;

    protected float tempMin;

    protected float tempMax;

    public int getId() {
        return id;
    }

    public float getClouds() {
        return clouds;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getMessage() {
        return message;
    }

    public float getTempMax() {
        return tempMax;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getTempMin() {
        return tempMin;
    }

    public float getPressure() {
        return pressure;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getWindDegree() {
        return windDegree;
    }

    public int getCityId() {
        return cityId;
    }

    public long getDate() {
        return date;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDescription() {
        return description;
    }

    public int getWeatherIconId() {
        return weatherIconId;
    }

    protected Forecast(@NonNull Forecast forecast){
        this.weatherIconId = forecast.getWeatherIconId();
        this.id = forecast.getCityId();
        this.message = forecast.getMessage();
        this.cityName = forecast.getCityName();
        this.cityId = forecast.getCityId();
        this.date = forecast.getDate();
        this.description = forecast.getDescription();
        this.clouds = forecast.getClouds();
        this.windSpeed = forecast.getWindSpeed();
        this.windDegree = forecast.getWindDegree();
        this.temperature = forecast.getTemperature();
        this.pressure = forecast.getPressure();
        this.humidity = forecast.getHumidity();
        this.tempMin = forecast.getTempMin();
        this.tempMax = forecast.getTempMax();
    }

    public Forecast(
            float message,
            @NonNull String cityName,
            int cityId,
            long date,
            int weatherIconId,
            @NonNull String description,
            float clouds,
            float windSpeed,
            float windDegree,
            float temperature,
            float pressure,
            float humidity,
            float tempMin,
            float tempMax){
        this.message = message;
        this.cityName = cityName;
        this.cityId = cityId;
        this.date = date;
        this.weatherIconId = weatherIconId;
        this.description = description;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }
}
