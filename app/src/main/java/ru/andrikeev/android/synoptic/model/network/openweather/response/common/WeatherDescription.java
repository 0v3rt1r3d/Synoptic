package ru.andrikeev.android.synoptic.model.network.openweather.response.common;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


/**
 * Weather description.
 */

@AutoValue
public abstract class WeatherDescription {

    @SerializedName("id")
    public abstract int id();

    @SerializedName("description")
    public abstract String description();

    public static TypeAdapter<WeatherDescription> typeAdapter(Gson gson){
        return new AutoValue_WeatherDescription.GsonTypeAdapter(gson);
    }
}
