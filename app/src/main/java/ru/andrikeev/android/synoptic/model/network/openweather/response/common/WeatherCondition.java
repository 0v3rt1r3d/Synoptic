package ru.andrikeev.android.synoptic.model.network.openweather.response.common;

import com.google.gson.annotations.SerializedName;

/**
 * Weather condition.
 */
public class WeatherCondition {

    @SerializedName("temp")
    private float temperature;

    @SerializedName("pressure")
    private float pressure;

    @SerializedName("humidity")
    private float humidity;

    @SerializedName("temp_min")
    private float minTemperatire;

    @SerializedName("temp_max")
    private float maxTemperature;

    public float getTemperature() {
        return temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getMinTemperatire() {
        return minTemperatire;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }
}
