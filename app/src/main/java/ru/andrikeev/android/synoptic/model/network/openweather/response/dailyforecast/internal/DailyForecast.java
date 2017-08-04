package ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast.internal;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.andrikeev.android.synoptic.model.network.openweather.response.common.WeatherDescription;

/**
 * Created by overtired on 01.08.17.
 */

@AutoValue
public abstract class DailyForecast {
    @SerializedName("dt")
    public abstract long date();

    @SerializedName("temp")
    public abstract Temp temp();

    @SerializedName("weather")
    public abstract List<WeatherDescription> weather();

    @SerializedName("pressure")
    public abstract float pressure();

    @SerializedName("humidity")
    public abstract float humidity();

    @SerializedName("speed")
    public abstract float speed();

    @SerializedName("deg")
    public abstract float degree();

    @SerializedName("clouds")
    public abstract float clouds();
}
