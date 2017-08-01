package ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.andrikeev.android.synoptic.model.network.openweather.response.common.City;
import ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast.internal.DailyForecast;

/**
 * Created by overtired on 01.08.17.
 */

public class DailyForecastResponse {
    @SerializedName("city")
    private City city;

    @SerializedName("list")
    private List<DailyForecast> forcastesList;

    @SerializedName("message")
    private float message;

    public City getCity() {
        return city;
    }

    public List<DailyForecast> getForcastesList() {
        return forcastesList;
    }
}
