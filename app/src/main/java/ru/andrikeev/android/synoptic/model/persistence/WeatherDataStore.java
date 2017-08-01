package ru.andrikeev.android.synoptic.model.persistence;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
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

    public void cacheWeather(@NonNull final Weather weather) {
        Single<WeatherEntity> insertion = dataStore.insert(new WeatherEntity(weather)).subscribeOn(Schedulers.io());

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
}
