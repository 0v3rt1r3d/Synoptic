package ru.andrikeev.android.synoptic.model.network.openweather;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.network.RemoteService;
import ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast.DailyForecastResponse;
import ru.andrikeev.android.synoptic.model.network.openweather.response.forecast.ForecastResponse;
import ru.andrikeev.android.synoptic.model.network.openweather.response.weather.WeatherResponse;


public class OpenWeatherService implements RemoteService {

    public static final String API_KEY_NAME = "api_key";

    private OpenWeatherApi api;

    private Settings settings;

    private String apiKey;

    @Inject
    OpenWeatherService(@NonNull OpenWeatherApi api,
                       @NonNull Settings settings,
                       @NonNull @Named(API_KEY_NAME) String apiKey) {
        this.api = api;
        this.settings = settings;
        this.apiKey = apiKey;
    }

    public Single<WeatherResponse> getWeather(long cityId) {
        return api.getWeatherForCity(apiKey, settings.getLocale(), cityId)
                .subscribeOn(Schedulers.io());
    }

    public Single<WeatherResponse> getWeather(double lat, double lon){
        return api.getWeatherForCoords(apiKey,settings.getLocale(),lon,lat)
                .subscribeOn(Schedulers.io());
    }

    public Single<ForecastResponse> getForecast(long cityId){
        return api.getForecast(apiKey,settings.getLocale(),cityId)
                .subscribeOn(Schedulers.io());
    }

    public Single<DailyForecastResponse> getDailyForecast(long cityId, int days){
        return api.getDailyForecast(apiKey,settings.getLocale(),days,cityId)
                .subscribeOn(Schedulers.io());
    }
}
