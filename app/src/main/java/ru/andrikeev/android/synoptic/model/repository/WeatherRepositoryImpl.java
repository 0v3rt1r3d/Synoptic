package ru.andrikeev.android.synoptic.model.repository;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.ModelsConverter;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.model.network.RemoteService;
import ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast.DailyForecastResponse;
import ru.andrikeev.android.synoptic.model.network.openweather.response.forecast.ForecastResponse;
import ru.andrikeev.android.synoptic.model.network.openweather.response.weather.WeatherResponse;
import ru.andrikeev.android.synoptic.model.persistence.CacheService;
import ru.andrikeev.android.synoptic.model.persistence.Weather;
import timber.log.Timber;

public class WeatherRepositoryImpl implements WeatherRepository {

    /**
     * From API documentation: "Do not send requests more than 1 time per 10 minutes
     * from one device/one API key. Normally the weather is not changing so frequently."
     */
    private static final long FETCH_MIN_TIMEOUT = 600_000_000L;

    @NonNull
    private PublishSubject<Resource<WeatherModel>> subject = PublishSubject.create();

    @NonNull
    private RemoteService remoteService;

    @NonNull
    private ModelsConverter converter;

    @NonNull
    private CacheService cacheService;

    @NonNull
    private Settings settings;

    @Inject
    WeatherRepositoryImpl(@NonNull RemoteService remoteService,
                          @NonNull CacheService cacheService,
                          @NonNull ModelsConverter converter,
                          @NonNull Settings settings) {
        this.remoteService = remoteService;
        this.cacheService = cacheService;
        this.converter = converter;
        this.settings = settings;
    }

    private static boolean shouldFetchWeather(@NonNull Weather weather) {
        return System.currentTimeMillis() - weather.getTimestamp() > FETCH_MIN_TIMEOUT;
    }

    @NonNull
    public Observable<Resource<WeatherModel>> loadWeather() {
        return cacheService.getWeather(settings.getCityId())
                .onErrorResumeNext(loadRemoteAndSave(settings.getCityId()))
                .map(weather -> {
                    if (shouldFetchWeather(weather)) {
                        fetchWeather();
                        return Resource.fetching(converter.toViewModel(weather));
                    } else {
                        return Resource.success(converter.toViewModel(weather));
                    }
                })
                .toObservable()
                .concatWith(subject)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    private Single<Weather> loadRemoteAndSave(long cityId) {
        return remoteService.getWeather(cityId)
                .map(weatherResponse -> {
                    Timber.d("Weather loaded from api: %s", weatherResponse);
                    Weather weather = converter.toCacheModel(weatherResponse);
                    cacheService.cacheWeather(weather);
                    return weather;
                });
    }

    @NonNull
    private Single<Weather> loadRemoteAndSave(double lon, double lat) {
        return remoteService.getWeather(lat, lon)
                .map(weatherResponse -> {
                    Timber.d("Weather loaded from api: %s", weatherResponse);
                    Weather weather = converter.toCacheModel(weatherResponse);
                    settings.setCityId(weather.getCityId());
                    cacheService.cacheWeather(weather);
                    return weather;
                });
    }

    public void fetchWeather() {
        loadRemoteAndSave(settings.getCityId()).subscribe(
                new Consumer<Weather>() {
                    @Override
                    public void accept(@NonNull Weather weather) throws Exception {
                        Timber.d("Weather fetched: %s", weather);
                        subject.onNext(Resource.success(converter.toViewModel(weather)));
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Timber.e(throwable, "Error fetching weather");
                        subject.onNext(Resource.<WeatherModel>error(throwable));
                    }
                });
    }

    public void fetchWeather(double lon, double lat) {
        loadRemoteAndSave(lon, lat)
                .subscribe(weather -> {
                            Timber.d("Weather fetched: %s", weather);
                            subject.onNext(Resource.success(converter.toViewModel(weather)));
                        },
                        throwable -> {
                            Timber.e(throwable, "Error fetching weather");
                            subject.onNext(Resource.error(throwable));
                        });
    }

    @Override
    public void fetchDailyForecast() {
        remoteService.getDailyForecast(524901, 5)
                .subscribeOn(Schedulers.io())
                .subscribe((DailyForecastResponse response) ->
                                Timber.d("DailyForecast loaded"),
                        Throwable::printStackTrace);
    }

    @Override
    public void fetchForecast() {
        remoteService.getForecast(524901)
                .subscribeOn(Schedulers.io())
                .subscribe(forecastResponse ->
                                Timber.d("Forecast loaded"),
                        Throwable::printStackTrace);
    }
}
