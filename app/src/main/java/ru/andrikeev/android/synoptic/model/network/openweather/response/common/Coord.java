package ru.andrikeev.android.synoptic.model.network.openweather.response.common;

import com.google.gson.annotations.SerializedName;

/**
 * Coordinates.
 */
public class Coord {

    @SerializedName("lon")
    private float longitude;

    @SerializedName("lat")
    private float latitude;

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }
}
