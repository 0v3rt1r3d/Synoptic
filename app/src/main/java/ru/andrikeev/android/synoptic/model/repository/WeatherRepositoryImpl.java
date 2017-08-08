package ru.andrikeev.android.synoptic.model.repository;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.ModelsConverter;
import ru.andrikeev.android.synoptic.model.data.DailyForecastModel;
import ru.andrikeev.android.synoptic.model.data.ForecastModel;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.model.network.RemoteService;
import ru.andrikeev.android.synoptic.model.persistence.CacheService;
import ru.andrikeev.android.synoptic.model.persistence.DailyForecast;
import ru.andrikeev.android.synoptic.model.persistence.Forecast;
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
        return System.currentTimeMillis() - weather.timestamp() > FETCH_MIN_TIMEOUT;
    }

    @NonNull
    public Observable<Resource<WeatherModel>> loadWeather() {
        return cacheService.getWeather(settings.getCityId())
                .onErrorResumeNext(loadWeatherRemoteAndSave(settings.getCityId()))
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

    @Override
    public Single<ForecastModel> loadForecasts() {
        return cacheService.getForecasts(settings.getCityId(),0.0f)
                .onErrorResumeNext(loadForecastRemoteAndSave(settings.getCityId()))
                .map(forecasts -> converter.toForecastViewModel(forecasts))
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<DailyForecastModel> loadDailyForecast() {
        return cacheService.getDailyForecasts(settings.getCityId(),0.0f)
                .onErrorResumeNext(loadDailyForecastAndSave(settings.getCityId()))
                .map(forecasts -> converter.toDailyForecastViewModel(forecasts))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    private Single<Weather> loadWeatherRemoteAndSave(long cityId) {
        return remoteService.getWeather(cityId)
                .map(weatherResponse -> {
                    Timber.d("Weather loaded from api: %s", weatherResponse);
                    Weather weather = converter.toCacheModel(weatherResponse);
                    cacheService.cacheWeather(weather);
                    return weather;
                });
    }

    @NonNull
    private Single<List<Forecast>> loadForecastRemoteAndSave(long cityId) {
        return remoteService.getForecast(cityId)
                .map(forecastResponse -> {
                    Timber.d("Forecast loaded from api: %s",forecastResponse);
                    List<Forecast> forecasts = converter.toForecastCacheModel(forecastResponse);
                    cacheService.cacheForecasts(forecasts);
                    return forecasts;
                });
    }

    @NonNull
    private Single<Weather> loadWeatherRemoteAndSave(double lon, double lat) {
        return remoteService.getWeather(lat, lon)
                .map(weatherResponse -> {
                    Timber.d("Weather loaded from api: %s", weatherResponse);
                    Weather weather = converter.toCacheModel(weatherResponse);
                    settings.setCityId(weather.cityId());
                    cacheService.cacheWeather(weather);
                    return weather;
                });
    }

    private Single<List<DailyForecast>> loadDailyForecastAndSave(long cityId){
        return remoteService.getDailyForecast(cityId,16)//todo: count
                .map(dailyForecastResponse -> {
                    Timber.d("Forecast loaded from api: %s",dailyForecastResponse);
                    List<DailyForecast> forecasts = converter.toDailyForecastCacheModel(dailyForecastResponse);
                    cacheService.cacheDailyForecasts(forecasts);
                    return forecasts;
                });
    }

    public void fetchWeather() {
        loadWeatherRemoteAndSave(settings.getCityId()).subscribe(
                weather -> {
                    Timber.d("Weather fetched: %s", weather);
                    subject.onNext(Resource.success(converter.toViewModel(weather)));
                },
                throwable -> {
                    Timber.e(throwable, "Error fetching weather");
                    subject.onNext(Resource.<WeatherModel>error(throwable));
                });
    }

    public void fetchWeather(double lon, double lat) {
        loadWeatherRemoteAndSave(lon, lat)
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
        loadDailyForecastAndSave(settings.getCityId())
                .subscribe(
                        dailyForecasts -> Timber.d("Forecasts fetched: %s",dailyForecasts),
                        throwable -> Timber.e(throwable,"Error fetching forecast")
                );
    }

    @Override
    public void fetchForecast() {
        loadForecastRemoteAndSave(settings.getCityId())
                .subscribe(
                        forecasts -> Timber.d("Forecasts fetched: %s",forecasts),
                        throwable -> Timber.e(throwable,"Error fetching forecast")
                );
//        remoteService.getForecast(settings.getCityId())
//                .subscribeOn(Schedulers.io())
//                .subscribe(forecastResponse ->
//                                Timber.d("Forecast loaded"),
//                        Throwable::printStackTrace);
    }
}
