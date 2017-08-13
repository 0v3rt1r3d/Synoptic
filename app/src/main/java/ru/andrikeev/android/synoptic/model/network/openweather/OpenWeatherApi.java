package ru.andrikeev.android.synoptic.model.network.openweather;

import android.support.annotation.IntRange;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast.DailyForecastResponse;
import ru.andrikeev.android.synoptic.model.network.openweather.response.forecast.ForecastResponse;
import ru.andrikeev.android.synoptic.model.network.openweather.response.weather.WeatherResponse;

/**
 * Open Weather Map API.
 */
public interface OpenWeatherApi {

    String API_KEY = "appid";

    String CITY_ID = "id";
    String LATITUDE = "lat";
    String LONGITUDE = "lon";
    String LANGUAGE = "lang";
    String DAYS_COUNT = "cnt";

    @GET("weather")
    Single<WeatherResponse> getWeatherForCoords(
            @Query(API_KEY) String apiKey,
            @Query(LANGUAGE) String language,
            @Query(LONGITUDE) double longitude,
            @Query(LATITUDE) double latitude);

    @GET("weather")
    Single<WeatherResponse> getWeatherForCity(
            @Query(API_KEY) String apiKey,
            @Query(LANGUAGE) String language,
            @Query(CITY_ID) long cityId);

    @GET("forecast/daily")
    Single<DailyForecastResponse> getDailyForecast(
            @Query(API_KEY) String apiKey,
            @Query(LANGUAGE) String language,
            @Query(DAYS_COUNT) @IntRange(from = 1,to = 16) int daysCount,
            @Query(CITY_ID) long cityId
    );

    @GET("forecast")
    Single<ForecastResponse> getForecast(
            @Query(API_KEY) String apiKey,
            @Query(LANGUAGE) String language,
            @Query(CITY_ID) long cityId
    );
}
