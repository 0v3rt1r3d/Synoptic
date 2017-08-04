package ru.andrikeev.android.synoptic.model.network.openweather.response.common;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

/**
 * Created by overtired on 01.08.17.
 */

@AutoValue
public abstract class City {
    @SerializedName("name")
    public abstract String cityName();

    @SerializedName("id")
    public abstract int id();


    //todo: does the field used?
    @SerializedName("coord")
    public abstract Coord coord();
}
