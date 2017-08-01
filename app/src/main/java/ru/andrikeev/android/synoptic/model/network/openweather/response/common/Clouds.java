package ru.andrikeev.android.synoptic.model.network.openweather.response.common;

import com.google.gson.annotations.SerializedName;

/**
 * Clouds.
 */
public class Clouds {

    @SerializedName("all")
    private float percents;

    public float getPercents() {
        return percents;
    }
}
