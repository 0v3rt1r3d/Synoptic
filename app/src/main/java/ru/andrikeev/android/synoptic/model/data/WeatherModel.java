package ru.andrikeev.android.synoptic.model.data;

import android.support.annotation.NonNull;

/**
 * Weather model for UI.
 */
public class WeatherModel {

    /**
     * City name.
     */
    @NonNull
    private String cityName;

    /**
     * Date of last weather update.
     */
    @NonNull
    private String date;

    /**
     * Resource id of weather icon.
     */
    private int weatherIconId;

    /**
     * Description of weather condition.
     */
    @NonNull
    private String description;

    /**
     * Temperature.
     */
    @NonNull
    private String temperature;

    /**
     * Temperature units (˚C / ˚F).
     */
    @NonNull
    private String temperatureUnits;

    /**
     * Pressure.
     */
    @NonNull
    private String pressure;

    /**
     * Humidity.
     */
    @NonNull
    private String humidity;

    /**
     * Wind windSpeed.
     */
    @NonNull
    private String windSpeed;

    /**
     * Resource id of wind direction icon.
     */
    private int windDirectionIconId;

    /**
     * Clouds (%).
     */
    @NonNull
    private String clouds;

    public WeatherModel(@NonNull String cityName,
                        @NonNull String date,
                        int weatherIconId,
                        @NonNull String description,
                        @NonNull String temperature,
                        @NonNull String temperatureUnits,
                        @NonNull String pressure,
                        @NonNull String humidity,
                        @NonNull String windSpeed,
                        int windDirectionIconId,
                        @NonNull String clouds) {
        this.cityName = cityName;
        this.date = date;
        this.weatherIconId = weatherIconId;
        this.description = description;
        this.temperature = temperature;
        this.temperatureUnits = temperatureUnits;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirectionIconId = windDirectionIconId;
        this.clouds = clouds;
    }

    @NonNull
    public String getCityName() {
        return cityName;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public int getWeatherIconId() {
        return weatherIconId;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getTemperature() {
        return temperature;
    }

    @NonNull
    public String getTemperatureUnits() {
        return temperatureUnits;
    }

    @NonNull
    public String getPressure() {
        return pressure;
    }

    @NonNull
    public String getHumidity() {
        return humidity;
    }

    @NonNull
    public String getWindSpeed() {
        return windSpeed;
    }

    public int getWindDirectionIconId() {
        return windDirectionIconId;
    }

    @NonNull
    public String getClouds() {
        return clouds;
    }
}
