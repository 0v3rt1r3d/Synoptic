package ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast.internal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.andrikeev.android.synoptic.model.network.openweather.response.common.WeatherDescription;

/**
 * Created by overtired on 01.08.17.
 */

public class DailyForecast {
    @SerializedName("dt")
    private long date;

    @SerializedName("temp")
    private Temp temp;

    @SerializedName("weather")
    private List<WeatherDescription> weather;

    @SerializedName("pressure")
    private float pressure;

    @SerializedName("humidity")
    private float humidity;

    @SerializedName("speed")
    private float speed;

    @SerializedName("deg")
    private float degree;

    @SerializedName("clouds")
    private float clouds;


    public List<WeatherDescription> getWeather() {
        return weather;
    }

    public Temp getTemp() {
        return temp;
    }

    public float getPressure() {
        return pressure;
    }

    public float getSpeed() {
        return speed;
    }

    public float getDegree() {
        return degree;
    }

    public float getClouds() {
        return clouds;
    }
}
