package ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast.internal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by overtired on 01.08.17.
 */

public class Temp {
    @SerializedName("day")
    private float day;

    @SerializedName("night")
    private float night;

    @SerializedName("eve")
    private float evening;

    @SerializedName("morn")
    private float morning;

    @SerializedName("min")
    private float min;

    @SerializedName("max")
    private float max;
}
