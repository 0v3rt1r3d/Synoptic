package ru.andrikeev.android.synoptic.model.persistence;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
        return dataStore.select(Weather.class)
                .where(WeatherType.CITY_ID.eq(cityId))
                .get()
                .observable()
                .subscribeOn(Schedulers.io())
                .singleOrError()
                .map(weather -> {
                    Timber.d("Weather restored from cache: %s", weather);
                    return weather;
                });
    }

    @Override
    public Single<List<Forecast>> getForecasts(long cityId, float message) {
        return dataStore.select(Forecast.class)
                .where(ForecastType.CITY_ID.eq(cityId))
                .get()
                .observable()
                .subscribeOn(Schedulers.io())
                .toList()
                .map(forecasts -> {
                    if (forecasts.size() > 0) {
                        Timber.d("Forecasts restored from cache: %s", forecasts);
                        return forecasts;
                    } else {
                        throw new Exception("Where are not forecasts in the database");
                    }
                });
    }

    @Override
    public Single<List<DailyForecast>> getDailyForecasts(long cityId, float message) {
        return dataStore.select(DailyForecast.class)
                .where(DailyForecastType.CITY_ID.eq(cityId))
                .get()
                .observable()
                .subscribeOn(Schedulers.io())
                .toList()
                .map(forecasts -> {
                    Timber.d("Forecasts restored from cache: %s", forecasts);
                    return forecasts;
                });
    }

    @Override
    public Single<City> getCity(long cityId) {
        return dataStore.select(City.class)
                .where(CityType.CITY_ID.eq(cityId))
                .get()
                .observable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .singleOrError();
    }

    @Override
    public Single<List<City>> getCities() {
        return dataStore.select(City.class)
                .get()
                .observable()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<City> removeCity(@NonNull City city) {
        return dataStore.delete(City.class)
                .where(CityType.CITY_ID.eq(city.cityId()))
                .get()
                .single()
                .subscribeOn(Schedulers.io())
                .map(integer -> city);
    }


    public void cacheWeather(@NonNull final Weather weather) {
        Single<Weather> insertion = dataStore.insert(weather);

        dataStore.select(Weather.class)
                .where(WeatherType.CITY_ID.eq(weather.cityId()))
                .get()
                .observable()
                .subscribeOn(Schedulers.io())
                .singleOrError()
                .onErrorResumeNext(insertion)
                .flatMap(weather1 -> dataStore.delete(Weather.class)
                        .where(WeatherType.CITY_ID.eq(weather1.cityId()))
                        .get()
                        .single()
                        .subscribeOn(Schedulers.io())
                        .flatMap(integer -> insertion))
                .subscribe(weather1 -> Timber.d("Weather was cached: %s", weather1.toString()),
                        throwable -> Timber.d(throwable, "Weather was not cached"));
    }

    @Override
    public void cacheForecasts(@NonNull List<Forecast> forecasts) {
        dataStore.delete(Forecast.class)
                .where(ForecastType.CITY_ID.eq(forecasts.get(0).cityId()))
                .get()
                .single()
                .doOnSuccess(integer -> {
                    Observable.fromIterable(forecasts)
                            .subscribeOn(Schedulers.io())
                            .flatMap(forecast -> dataStore.insert(forecast, Integer.class)
                                    .toObservable())
                            .toList();
                })
                .subscribe(integer -> {
                    Timber.d("Old forecasts were removed");
                    Timber.d("New forecasts were stored");
                }, throwable -> {
                    Timber.d("Forecast storing error");
                });
    }

    @Override
    public void cacheDailyForecasts(@NonNull List<DailyForecast> forecasts) {
        dataStore.delete(DailyForecast.class)
                .where(ForecastType.CITY_ID.eq(forecasts.get(0).cityId()))
                .get()
                .single()
                .doOnSuccess(integer -> {
                    Observable.fromIterable(forecasts)
                            .subscribeOn(Schedulers.io())
                            .flatMap(forecast -> dataStore.insert(forecast, Integer.class).toObservable())
                            .toList();
                })
                .subscribe(integer -> {
                    Timber.d("Old DailyForecasts were removed");
                    Timber.d("New DailyForecasts were stored");
                },throwable -> {
                    Timber.d(throwable,"DailyForecast storing error");
                });
    }

    @Override
    public void cacheCity(@NonNull City city) {
        Single<City> insertion = dataStore.insert(city)
                .subscribeOn(Schedulers.io());

        dataStore.select(City.class)
                .where(CityType.CITY_ID.eq(city.cityId()))
                .get()
                .observable()
                .subscribeOn(Schedulers.io())
                .singleOrError()
                .onErrorResumeNext(insertion)
                .subscribe(
                        city1 -> Timber.d("City was cached %s:", city.toString()),
                        throwable -> Timber.d(throwable, "Did not cache city"));
    }

    @Override
    public void updateLastCityMessage(long cityId, float message) {
        dataStore.update(City.class)
                .set(CityType.LAST_MESSAGE, message)
                .where(CityType.CITY_ID.eq(cityId))
                .get()
                .single()
                .observeOn(Schedulers.io());
    }
}
