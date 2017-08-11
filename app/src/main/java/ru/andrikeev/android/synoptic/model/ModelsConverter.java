package ru.andrikeev.android.synoptic.model;

import android.support.annotation.NonNull;

import java.util.List;

import ru.andrikeev.android.synoptic.model.data.DailyForecastModel;
import ru.andrikeev.android.synoptic.model.data.ForecastModel;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast.DailyForecastResponse;
import ru.andrikeev.android.synoptic.model.network.openweather.response.forecast.ForecastResponse;
import ru.andrikeev.android.synoptic.model.network.openweather.response.weather.WeatherResponse;
import ru.andrikeev.android.synoptic.model.persistence.DailyForecast;
import ru.andrikeev.android.synoptic.model.persistence.Forecast;
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
    Weather toWeatherCacheModel(@NonNull WeatherResponse weatherResponse);


    List<DailyForecast> toDailyForecastCacheModel(@NonNull DailyForecastResponse forecastResponse);

    DailyForecastModel toDailyForecastViewModel(@NonNull List<DailyForecast> forecast);

    List<Forecast> toForecastCacheModel(@NonNull ForecastResponse forecastResponse);

    ForecastModel toForecastViewModel(@NonNull List<Forecast> forecast);
}
