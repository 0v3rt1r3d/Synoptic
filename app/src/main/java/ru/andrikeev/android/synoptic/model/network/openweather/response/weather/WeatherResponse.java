package ru.andrikeev.android.synoptic.model.network.openweather.response.weather;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.List;

import ru.andrikeev.android.synoptic.model.network.openweather.response.common.Clouds;
import ru.andrikeev.android.synoptic.model.network.openweather.response.common.WeatherCondition;
import ru.andrikeev.android.synoptic.model.network.openweather.response.common.WeatherDescription;
import ru.andrikeev.android.synoptic.model.network.openweather.response.common.Wind;

/**
 * Weather model from response.
 */
@AutoValue
public abstract class WeatherResponse {

    @SerializedName("weather")
    public abstract List<WeatherDescription> weatherDescription();

    @SerializedName("main")
    public abstract WeatherCondition weatherCondition();

    @SerializedName("wind")
    public abstract Wind wind();

    @SerializedName("clouds")
    public abstract Clouds clouds();

    @SerializedName("dt")
    public abstract long timestamp();

    @SerializedName("name")
    public abstract String city();

    @SerializedName("id")
    public abstract long cityId();

    public static TypeAdapter<WeatherResponse> typeAdapter(Gson gson){
        return new AutoValue_WeatherResponse.GsonTypeAdapter(gson);
    }
}
