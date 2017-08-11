package ru.andrikeev.android.synoptic.model.persistence;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.LongConsumer;
import io.reactivex.schedulers.Schedulers;
import io.requery.Persistable;
import io.requery.reactivex.ReactiveEntityStore;
import ru.andrikeev.android.synoptic.model.repository.Resource;
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
                //.where(ForecastType.CITY_ID.eq(cityId).and(ForecastType.MESSAGE.eq(message)))
                //todo: normal getting with message
                .where(ForecastType.CITY_ID.eq(cityId))
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
        Observable.fromIterable(forecasts)
                .subscribeOn(Schedulers.io())
                .flatMap(forecast -> dataStore.insert(forecast, Integer.class)
                        .toObservable())
                .toList()
                .doOnSuccess(integers -> {
                    long cityId = forecasts.get(0).cityId();
                    float message = forecasts.get(0).message();

                    updateLastCityMessage(cityId, message);
                    dataStore.delete(Forecast.class)
                            .where(ForecastType.CITY_ID.equal(cityId).and(ForecastType.MESSAGE.notEqual(message)))
                            .get()
                            .single()
                            .subscribeOn(Schedulers.io())
                            .subscribe(integer -> Timber.d("Old forecasts were removed"),
                                    throwable -> Timber.d(throwable, "Old forecasts were not removed"));
                })
                .subscribe(forecast1 -> Timber.d("New Forecasts were cached"),
                        throwable -> Timber.d(throwable, "New Forecasts were no cached"));
    }

    @Override
    public void cacheDailyForecasts(@NonNull List<DailyForecast> forecasts) {
        Observable.fromIterable(forecasts)
                .subscribeOn(Schedulers.io())
                .flatMap(forecast -> dataStore.insert(forecast, Integer.class).toObservable())
                .toList()
                .doOnSuccess(integers -> {
                    long cityId = forecasts.get(0).cityId();
                    float message = forecasts.get(0).message();

                    updateLastCityMessage(cityId, message);
                    dataStore.delete(DailyForecast.class)
                            .where(DailyForecastType.CITY_ID.equal(cityId).and(DailyForecastType.MESSAGE.notEqual(message)))
                            .get()
                            .single()
                            .subscribeOn(Schedulers.io())
                            .subscribe(integer -> Timber.d("Old forecasts were removed"),
                                    throwable -> Timber.d(throwable, "Old forecasts were not removed"));
                })
                .subscribe(forecast1 -> Timber.d("New DailyForecasts were cached"),
                        throwable -> Timber.d(throwable, "New DailyForecasts were no cached"));
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
