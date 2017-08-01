package ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast.internal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by overtired on 01.08.17.
 */

public class Temp {
    @SerializedName("day")
    private float tempDay;

    @SerializedName("night")
    private float tempNight;

    @SerializedName("eve")
    private float tempEvening;

    @SerializedName("morn")
    private float tempMorning;

    @SerializedName("min")
    private float tempMin;

    @SerializedName("max")
    private float tempMax;

    public float getTempDay() {
        return tempDay;
    }

    public float getTempNight() {
        return tempNight;
    }

    public float getTempEvening() {
        return tempEvening;
    }

    public float getTempMorning() {
        return tempMorning;
    }

    public float getTempMin() {
        return tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }
}
