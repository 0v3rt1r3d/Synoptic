package ru.andrikeev.android.synoptic.model.persistence;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.requery.Persistable;
import io.requery.reactivex.ReactiveEntityStore;
import timber.log.Timber;

/**
 * Local storage for cached weather.
 */
@Singleton
public class WeatherDataStore implements CacheService {

    @NonNull
    private ReactiveEntityStore<Persistable> dataStore;

    @Inject
    WeatherDataStore(@NonNull ReactiveEntityStore<Persistable> dataStore) {
        this.dataStore = dataStore;
    }

    @NonNull
    public Single<Weather> getWeather(long cityId) {
        return dataStore.select(WeatherEntity.class)
                .where(WeatherEntity.CITY_ID.eq(cityId))
                .get()
                .observable()
                .subscribeOn(Schedulers.io())
                .singleOrError()
                .map(weatherEntity -> {
                    Timber.d("Weather restored from cache: %s", weatherEntity);
                    return new Weather(weatherEntity);
                });
    }

//    @Override
//    public Single<List<Forecast>> getForecasts(long cityId) {
//        return dataStore.select(Forecast.class)
//                .where(ForecastType.CITY_ID.eq(cityId))
//                .get()
//                .observable()
//                .subscribeOn(Schedulers.io())
//                .map()
//    }

    public void cacheWeather(@NonNull final Weather weather) {
        Single<WeatherEntity> insertion = dataStore
                .insert(new WeatherEntity(weather))
                .subscribeOn(Schedulers.io());

        dataStore.select(WeatherEntity.class)
                .where(WeatherEntity.CITY_ID.eq(weather.cityId))
                .get()
                .observable()
                .subscribeOn(Schedulers.single())
                .singleOrError()
                .onErrorResumeNext(insertion)
                .flatMap(new Function<WeatherEntity, SingleSource<WeatherEntity>>() {
                    @Override
                    public SingleSource<WeatherEntity> apply(@NonNull WeatherEntity weatherEntity) throws Exception {
                        updateWeatherEntity(weatherEntity, weather);
                        return dataStore.update(weatherEntity);
                    }
                })
                .subscribe(
                        weatherEntity -> Timber.d("Weather cached: %s", weatherEntity),
                        throwable -> Timber.e(throwable, "Error caching weather")
                );
    }

    @Override
    public void cacheForecasts(@NonNull List<Forecast> forecasts) {
        for (Forecast forecast : forecasts) {
            cacheForecast(forecast);
        }
    }

    private static void updateWeatherEntity(@NonNull WeatherEntity entity, @NonNull Weather weather) {
        entity.setCityName(weather.getCityName());
        entity.setTimestamp(weather.getTimestamp());
        entity.setWeatherId(weather.getWeatherId());
        entity.setDescription(weather.getDescription());
        entity.setTemperature(weather.getTemperature());
        entity.setPressure(weather.getPressure());
        entity.setHumidity(weather.getHumidity());
        entity.setClouds(weather.getClouds());
        entity.setWindSpeed(weather.getWindSpeed());
        entity.setWindDegree(weather.getWindDegree());
    }

    public void cacheForecast(@NonNull Forecast forecast) {
        Disposable insertion = dataStore
                .insert(forecast,Integer.class)
                .subscribeOn(Schedulers.io())
                .subscribe(forecast1 -> {},throwable -> {
                    Timber.d(throwable,"Did not cache forecast");
                });

        dataStore.select(Forecast.class)
                .where(ForecastType.ID.eq(forecast.cityId()))
                .get()
                .observable()
                .subscribeOn(Schedulers.single())
                .singleOrError()
                .map(forecast1 -> {
                    return dataStore.delete(forecast1);
                    //updateWeatherEntity(weatherEntity, weather);
                    //return dataStore.update(weatherEntity);
                    //return null;
                })
                .subscribe(
                        completable -> {
                            Timber.d("Last forecast deleted");
                        },
                        throwable -> {Timber.d("Last forecast was not deleted");});
//                .subscribe(
//                        forecastEntity -> Timber.d("Forecast cached: %s", forecastEntity),
//                        throwable -> Timber.e(throwable, "Error caching forecast")
//                );

    }

    public void cacheDailyForecast(@NonNull DailyForecast dailyForecast) {
        Single<DailyForecast> insertion = dataStore
                .insert(dailyForecast)
                .subscribeOn(Schedulers.io());
    }
}
