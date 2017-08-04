package ru.andrikeev.android.synoptic.model.network.openweather.response.forecast;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.concurrent.locks.Condition;

import ru.andrikeev.android.synoptic.model.network.openweather.response.common.WeatherCondition;
import ru.andrikeev.android.synoptic.model.network.openweather.response.common.WeatherDescription;
import ru.andrikeev.android.synoptic.model.network.openweather.response.common.Clouds;
import ru.andrikeev.android.synoptic.model.network.openweather.response.common.Wind;

/**
 * Created by overtired on 01.08.17.
 */

@AutoValue
public abstract class Forecast {
    @SerializedName("dt")
    public abstract long date();

    @SerializedName("weather")
    public abstract List<WeatherDescription> weather();

    @SerializedName("clouds")
    public abstract Clouds clouds();

    @SerializedName("wind")
    public abstract Wind wind();

    @SerializedName("main")
    public abstract WeatherCondition condition();

    public static Forecast create(
            long date,
            List<WeatherDescription> weather,
            Clouds clouds,
            Wind wind,
            WeatherCondition condition){
        return new AutoValue_Forecast(date,weather,clouds,wind,condition);
    }
}
