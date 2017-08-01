package ru.andrikeev.android.synoptic.model.network.openweather.response.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.andrikeev.android.synoptic.model.network.openweather.response.common.WeatherCondition;
import ru.andrikeev.android.synoptic.model.network.openweather.response.common.WeatherDescription;
import ru.andrikeev.android.synoptic.model.network.openweather.response.common.Clouds;
import ru.andrikeev.android.synoptic.model.network.openweather.response.common.Wind;

/**
 * Created by overtired on 01.08.17.
 */

public class Forecast {
    @SerializedName("dt")
    private long date;

    @SerializedName("weather")
    private List<WeatherDescription> weather;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("main")
    private WeatherCondition condition;

    public WeatherCondition getCondition() {
        return condition;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public List<WeatherDescription> getWeather() {
        return weather;
    }

    public long getDate() {
        return date;
    }
}
