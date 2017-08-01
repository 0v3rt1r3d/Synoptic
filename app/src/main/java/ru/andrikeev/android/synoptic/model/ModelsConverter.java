package ru.andrikeev.android.synoptic.model;

import android.support.annotation.NonNull;

import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.model.network.openweather.response.weather.WeatherResponse;
import ru.andrikeev.android.synoptic.model.persistence.Weather;

/**
 * Converting models.
 */
public interface ModelsConverter {
    /**
     * Convert cached weather model to view model.
     */
    WeatherModel toViewModel(@NonNull Weather weather);

    /**
     * Converts remote weather model to cache model.
     */
    Weather toCacheModel(@NonNull WeatherResponse weatherResponse);
}
