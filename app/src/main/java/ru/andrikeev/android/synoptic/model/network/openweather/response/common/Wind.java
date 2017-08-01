package ru.andrikeev.android.synoptic.model.network.openweather.response.common;

import com.google.gson.annotations.SerializedName;

/**
 * Wind condition.
 */
public class Wind {

    @SerializedName("speed")
    private float speed;

    @SerializedName("deg")
    private float degree;

    public float getSpeed() {
        return speed;
    }

    public float getDegree() {
        return degree;
    }
}
