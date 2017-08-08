package ru.andrikeev.android.synoptic.model.data;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * Weather model for UI.
 */
@AutoValue
public abstract class WeatherModel {

    /**
     * Date of last weather update.
     */
    @NonNull
    public abstract String date();

    /**
     * Resource id of weather icon.
     */
    public abstract int weatherIconId();

    /**
     * Description of weather condition.
     */
    @NonNull
    public abstract String description();

    /**
     * Temperature.
     */
    @NonNull
    public abstract String temperature();

    /**
     * Temperature units (˚C / ˚F).
     */
    @NonNull
    public abstract String temperatureUnits();

    /**
     * Pressure.
     */
    @NonNull
    public abstract String pressure();

    /**
     * Humidity.
     */
    @NonNull
    public abstract String humidity();

    /**
     * Wind windSpeed.
     */
    @NonNull
    public abstract String windSpeed();

    /**
     * Resource id of wind direction icon.
     */
    public abstract int windDirectionIconId();

    /**
     * Clouds (%).
     */
    @NonNull
    public abstract String clouds();

    public static Builder Builder(){
        return new AutoValue_WeatherModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setDate(@NonNull String date);
        public abstract Builder setDescription(@NonNull String description);
        public abstract Builder setTemperature(@NonNull String temperature);
        public abstract Builder setTemperatureUnits(@NonNull String temperatureUnits);
        public abstract Builder setPressure(@NonNull String pressure);
        public abstract Builder setHumidity(@NonNull String humidity);
        public abstract Builder setWindSpeed(@NonNull String windSpeed);
        public abstract Builder setClouds(@NonNull String clouds);
        public abstract Builder setWeatherIconId(int weatherIconId);
        public abstract Builder setWindDirectionIconId(int windDirectionIconId);


        public abstract WeatherModel build();
    }
}
