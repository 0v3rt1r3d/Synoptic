package ru.andrikeev.android.synoptic.model.network.openweather.response.common;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Weather condition.
 */
@AutoValue
public abstract class WeatherCondition {

    @SerializedName("temp")
    public abstract float temperature();

    @SerializedName("pressure")
    public abstract float pressure();

    @SerializedName("humidity")
    public abstract float humidity();

    public static TypeAdapter<WeatherCondition> typeAdapter(Gson gson){
        return new AutoValue_WeatherCondition.GsonTypeAdapter(gson);
    }
}
