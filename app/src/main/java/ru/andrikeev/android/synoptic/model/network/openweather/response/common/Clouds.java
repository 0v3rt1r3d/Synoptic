package ru.andrikeev.android.synoptic.model.network.openweather.response.common;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Clouds.
 */
@AutoValue
public abstract class Clouds {

    @SerializedName("all")
    public abstract float percents();

    public static TypeAdapter<Clouds> typeAdapter(Gson gson){
        return new AutoValue_Clouds.GsonTypeAdapter(gson);
    }
}
