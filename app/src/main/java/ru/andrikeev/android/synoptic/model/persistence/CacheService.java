package ru.andrikeev.android.synoptic.model.persistence;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;

/**
 * Interface for local service (db, disk etc.).
 */
public interface CacheService {

    /**
     * Load cached weather for city with given id.
     *
     * @param cityId city id
     * @return {@link Single} with restored {@link Weather}
     */
    Single<Weather> getWeather(long cityId);

//    Single<List<Forecast>> getForecasts(long cityId);

    /**
     * Cache weather entity.
     *
     * @param weather weather
     */
    void cacheWeather(@NonNull final Weather weather);
    void cacheForecasts(@NonNull final List<Forecast> forecasts);
}
