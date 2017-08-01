package ru.andrikeev.android.synoptic.model.network.openweather.response.common;

import com.google.gson.annotations.SerializedName;

import ru.andrikeev.android.synoptic.model.network.openweather.response.common.Coord;

/**
 * Created by overtired on 01.08.17.
 */

public class City {
    @SerializedName("name")
    private String cityName;

    @SerializedName("id")
    private int id;

    @SerializedName("coord")
    private Coord coord;

    public Coord getCoord() {
        return coord;
    }

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }
}
