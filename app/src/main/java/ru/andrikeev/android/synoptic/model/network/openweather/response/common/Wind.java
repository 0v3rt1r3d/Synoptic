package ru.andrikeev.android.synoptic.model.network.openweather.response.common;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Wind condition.
 */

@AutoValue
public abstract class Wind {

    @SerializedName("speed")
    public abstract float speed();

    @SerializedName("deg")
    public abstract float degree();

    public static Wind create(float speed, float degree){
        return new AutoValue_Wind(speed,degree);
    }

    public static TypeAdapter<Wind> typeAdapter(Gson gson) {
        return new AutoValue_Wind.GsonTypeAdapter(gson);
    }
}
