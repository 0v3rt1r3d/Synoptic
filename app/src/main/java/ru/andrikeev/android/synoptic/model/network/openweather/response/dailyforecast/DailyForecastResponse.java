package ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.andrikeev.android.synoptic.model.network.openweather.response.common.City;
import ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast.internal.DailyForecast;

/**
 * Created by overtired on 01.08.17.
 */

@AutoValue
public abstract class DailyForecastResponse {
    @SerializedName("city")
    public abstract City city();

    @SerializedName("list")
    public abstract List<DailyForecast> forecastList();

    @SerializedName("message")
    public abstract float message();

//    public static DailyForecastResponse create(City city, List<DailyForecast> forecastList, float message){
//        return new AutoValue_DailyForecastResponse(city,forecastList,message);
//    }
}
