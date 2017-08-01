package ru.andrikeev.android.synoptic.model.network.openweather.response.common;

import com.google.gson.annotations.SerializedName;

/**
 * Weather description.
 */
public class WeatherDescription {

    @SerializedName("id")
    private int id;

    @SerializedName("main")
    private String shortDescription;

    @SerializedName("description")
    private String description;

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }
}
