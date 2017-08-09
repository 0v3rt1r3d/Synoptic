package ru.andrikeev.android.synoptic.model.data;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * Created by overtired on 01.08.17.
 */

@AutoValue
public abstract class ForecastItem {
    public abstract int weatherIconId();
    public abstract int windDirectionIconId();

    public abstract String date();
    public abstract String description();
    public abstract String clouds();
    public abstract String windSpeed();
    public abstract String temperature();
    public abstract String pressure();
    public abstract String humidity();
    public abstract String temperatureUnits();

    public static ForecastItem create(
            int weatherIconId,
            @NonNull String date,
            @NonNull String description,
            @NonNull String clouds,
            @NonNull String windSpeed,
            @NonNull String temperature,
            @NonNull String temperatureUnits,
            @NonNull String pressure,
            @NonNull String humidity,
            int windDirectionIconId) {
        return new AutoValue_ForecastItem(
                weatherIconId,
                windDirectionIconId,
                date,
                description,
                clouds,
                windSpeed,
                temperature,
                pressure,
                humidity,
                temperatureUnits);
    }
}
