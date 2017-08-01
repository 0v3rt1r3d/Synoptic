package ru.andrikeev.android.synoptic.model.network.openweather.response.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.andrikeev.android.synoptic.model.network.openweather.response.common.City;

/**
 * Created by overtired on 01.08.17.
 */

public class ForecastResponse {
    @SerializedName("city")
    private City city;

    @SerializedName("list")
    private List<Forecast> forecastsList;

    public City getCity() {
        return city;
    }

    public List<Forecast> getForecastsList() {
        return forecastsList;
    }
}
