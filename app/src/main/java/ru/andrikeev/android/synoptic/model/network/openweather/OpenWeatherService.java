package ru.andrikeev.android.synoptic.model.network.openweather;

import io.reactivex.Single;
import ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast.DailyForecastResponse;
import ru.andrikeev.android.synoptic.model.network.openweather.response.forecast.ForecastResponse;
import ru.andrikeev.android.synoptic.model.network.openweather.response.weather.WeatherResponse;

/**
 * Interface for remote (network) service.
 */
public interface OpenWeatherService {

    /**
     * Load current weather for city with given id.
     *
     * @param cityId city id
     * @return {@link Single} with loaded {@link WeatherResponse}
     */
    Single<WeatherResponse> getWeather(long cityId);
    Single<WeatherResponse> getWeather(double lat,double lon);
    Single<ForecastResponse> getForecast(long cityId);
    Single<DailyForecastResponse> getDailyForecast(long cityId, int days);
}
