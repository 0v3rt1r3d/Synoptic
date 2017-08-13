package ru.andrikeev.android.synoptic.model.network.openweather.response.common;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

/**
 * Coordinates.
 */
@AutoValue
public abstract class Coord {

    @SerializedName("lon")
    public abstract float longitude();

    @SerializedName("lat")
    public abstract float latitude();
}
