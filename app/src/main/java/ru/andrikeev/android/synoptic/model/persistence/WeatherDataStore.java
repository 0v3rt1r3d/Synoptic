package ru.andrikeev.android.synoptic.model.persistence;

import android.support.annotation.NonNull;

import java.util.ArrayList;
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
import io.reactivex.functions.LongConsumer;
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

    @Override
    public Single<List<Forecast>> getForecasts(long cityId, float message) {
        return dataStore.select(Forecast.class)
                //.where(ForecastType.CITY_ID.eq(cityId).and(ForecastType.MESSAGE.eq(message)))
                //todo: normal getting with message
                .where(ForecastType.CITY_ID.eq(cityId))
                .get()
                .observable()
                .subscribeOn(Schedulers.io())
                .toList()
                .map(forecasts -> {
                    Timber.d("Forecasts restored from cache: %s",forecasts);
                    return forecasts;
                });
    }

    @Override
    public Single<List<DailyForecast>> getDailyForecasts(long cityId, float message) {
        return dataStore.select(DailyForecast.class)
                //.where(DailyForecastType.CITY_ID.eq(cityId).and(ForecastType.MESSAGE.eq(message)))
                //todo message like above
                .where(DailyForecastType.CITY_ID.eq(cityId))
                .get()
                .observable()
                .subscribeOn(Schedulers.io())
                .toList()
                .map(forecasts -> {
                    Timber.d("Forecasts restored from cache: %s",forecasts);
                    return forecasts;
                });
    }

    @Override
    public Single<City> getCity(long cityId) {
        return dataStore.select(City.class)
                .where(CityType.CITY_ID.eq(cityId))
                .get()
                .observable()
                .singleOrError()
                .subscribeOn(Schedulers.io());
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

    @Override
    public void cacheDailyForecasts(@NonNull List<DailyForecast> forecasts) {
        for(DailyForecast forecast:forecasts){
            cacheDailyForecast(forecast);
        }
    }

    @Override
    public void cacheCity(@NonNull City city) {
        //todo:disposable?
        dataStore.insert(city)
                .subscribeOn(Schedulers.io())
                .subscribe(city1 -> {
                }, throwable ->
                        Timber.d(throwable, "Did not cache city"));

        //todo:check insertion
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
        //todo:disposable?
        dataStore.insert(forecast, Integer.class)
                .subscribeOn(Schedulers.io())
                .subscribe(forecast1 -> {
                }, throwable ->
                        Timber.d(throwable, "Did not cache forecast"));

        //todo:check insertion
        dataStore.select(Forecast.class)
                .where(ForecastType.MESSAGE.eq(forecast.message()))
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
                        throwable -> {
                            Timber.d("Last forecast was not deleted");
                        });
//                .subscribe(
//                        forecastEntity -> Timber.d("Forecast cached: %s", forecastEntity),
//                        throwable -> Timber.e(throwable, "Error caching forecast")
//                );

    }

    public void cacheDailyForecast(@NonNull DailyForecast dailyForecast) {
        //todo: disposable?
        dataStore.insert(dailyForecast, Integer.class)
                .subscribeOn(Schedulers.io())
                .subscribe(dailyForecast1->{},throwable -> Timber.d("Did not cache daily forecast"));
    }
}
