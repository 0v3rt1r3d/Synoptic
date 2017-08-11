package ru.andrikeev.android.synoptic.model.repository;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;

import io.reactivex.Single;
import ru.andrikeev.android.synoptic.model.data.DailyForecastModel;
import ru.andrikeev.android.synoptic.model.data.ForecastModel;
import ru.andrikeev.android.synoptic.model.data.SuggestionModel;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.model.network.googleplaces.ResponceConverter;
import ru.andrikeev.android.synoptic.model.persistence.City;

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
    Observable<Resource<ForecastModel>> loadForecasts();
    Observable<Resource<DailyForecastModel>> loadDailyForecast();
    Single<String> loadCity();
    Single<City> removeCachedCity(@NonNull City city);

    Single<List<SuggestionModel>> fetchPredictions(@NonNull String input);
    Single<List<City>> loadCachedCities();

    Single<Long> fetchCity(@NonNull String placeId);

    void fetchWeather();
    void fetchWeather(double lon, double lat);
    void fetchDailyForecast();
    void fetchForecast();
}
