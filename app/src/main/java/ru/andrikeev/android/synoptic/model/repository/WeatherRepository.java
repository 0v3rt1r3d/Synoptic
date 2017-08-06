package ru.andrikeev.android.synoptic.model.repository;

import io.reactivex.Observable;

import io.reactivex.Single;
import ru.andrikeev.android.synoptic.model.data.DailyForecastModel;
import ru.andrikeev.android.synoptic.model.data.ForecastModel;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;

/**
 * Interface for weather repository.
 */
public interface WeatherRepository {

    /**
     * Load weather (from cache or from network)
     *
     * @return {@link Observable} of {@link Resource<WeatherModel>}
     */
    Observable<Resource<WeatherModel>> loadWeather();
    Single<ForecastModel> loadForecasts();
    Single<DailyForecastModel> loadDailyForecast();

    /**
     * Start fetching, result will be returned in the subject received from {@link #loadWeather()}
     */
    void fetchWeather();
    void fetchWeather(double lon, double lat);
    void fetchDailyForecast();
    void fetchForecast();
}
