package ru.andrikeev.android.synoptic.model.persistence;

import android.support.annotation.NonNull;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Table;

/**
 * Created by overtired on 01.08.17.
 */

@Entity
@Table(name = "daily_forecast")
public class DailyForecast {
    @Key @Generated
    protected int id;

    protected float message;

    protected String cityName;

    protected int cityId;

    protected long date;

    protected String description;

    protected float clouds;

    protected float windSpeed;

    protected float windDegree;

    protected float pressure;

    protected float humidity;

    protected float tempMin;

    protected float tempMax;

    protected float tempMorning;

    protected float tempEvening;

    protected float tempNight;

    protected float tempDay;

    public String getDescription() {
        return description;
    }

    public String getCityName() {
        return cityName;
    }

    public long getDate() {
        return date;
    }

    public int getCityId() {
        return cityId;
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

    public float getPressure() {
        return pressure;
    }

    public float getTempDay() {
        return tempDay;
    }

    public float getTempEvening() {
        return tempEvening;
    }

    public float getTempMin() {
        return tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public float getTempMorning() {
        return tempMorning;
    }

    public float getTempNight() {
        return tempNight;
    }

    public float getWindDegree() {
        return windDegree;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public int getId() {
        return id;
    }

    protected DailyForecast(@NonNull DailyForecast forecast){
        this.id = forecast.getId();
        this.message = forecast.getMessage();
        this.cityName = forecast.getCityName();
        this.cityId = forecast.getCityId();
        this.date = forecast.getDate();
        this.description = forecast.getDescription();
        this.clouds = forecast.getClouds();
        this.windSpeed = forecast.getWindSpeed();
        this.windDegree = forecast.getWindDegree();
        this.pressure = forecast.getPressure();
        this.humidity = forecast.getHumidity();
        this.tempMin = forecast.getTempMin();
        this.tempMax = forecast.getTempMax();
        this.tempDay = forecast.getTempDay();
        this.tempNight = forecast.getTempNight();
        this.tempMorning = forecast.getTempMorning();
        this.tempEvening = forecast.getTempEvening();
    }

    public DailyForecast(
            float message,
            @NonNull String cityName,
            int cityId,
            long date,
            @NonNull String description,
            float clouds,
            float windSpeed,
            float windDegree,
            float pressure,
            float humidity,
            float tempMin,
            float tempMax,
            float tempDay,
            float tempNight,
            float tempMorning,
            float tempEvening){
        this.message = message;
        this.cityName = cityName;
        this.cityId = cityId;
        this.date = date;
        this.description = description;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.tempDay = tempDay;
        this.tempNight = tempNight;
        this.tempMorning = tempMorning;
        this.tempEvening = tempEvening;
    }
}
