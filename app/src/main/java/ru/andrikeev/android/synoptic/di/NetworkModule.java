package ru.andrikeev.android.synoptic.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.andrikeev.android.synoptic.BuildConfig;
import ru.andrikeev.android.synoptic.model.GsonAdapterFactory;
import ru.andrikeev.android.synoptic.model.network.googleplaces.GooglePlacesApi;
import ru.andrikeev.android.synoptic.model.network.openweather.OpenWeatherApi;

import static java.util.concurrent.TimeUnit.SECONDS;
import static ru.andrikeev.android.synoptic.model.network.googleplaces.GooglePlacesServiceImpl.API_KEY_PLACES;
import static ru.andrikeev.android.synoptic.model.network.openweather.OpenWeatherServiceImpl.API_KEY_NAME;

/**
 * Module for network dependencies.
 */
@Module
final class NetworkModule {

    private static final String BASE_URL_WEATHER = "base_url_weather";
    private static final String BASE_URL_PLACES = "base_url_places";

    /**
     * API key for Open Weather Api
     */
    @Provides
    @Singleton
    @Named(API_KEY_NAME)
    @NonNull
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @Singleton
    @Named(API_KEY_PLACES)
    @NonNull
    String provideApiPlacesKey(){
        return BuildConfig.API_KEY_PLACES;
    }

    /**
     * Base API URL.
     */
    @Provides
    @Singleton
    @Named(BASE_URL_PLACES)
    @NonNull
    String provideBaseUrlPlaces() {
        return BuildConfig.BASE_URL_PLACES;
    }

    /**
     * Base API URL.
     */
    @Provides
    @Singleton
    @Named(BASE_URL_WEATHER)
    @NonNull
    String provideBaseUrl() {
        return BuildConfig.BASE_URL_WEATHER;
    }

    /**
     * Provides OkHttpClient for http requests with cache.
     *
     * @param cache filesystem cache storage
     * @return http client
     */
    @Provides
    @Singleton
    @NonNull
    OkHttpClient provideOkHttpClient(@NonNull Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .readTimeout(10L, SECONDS)
                .build();
    }

    /**
     * Provides filesystem cache of 10Mb in {@link Context#getCacheDir()}
     *
     * @param context application context
     * @return filesystem cache of 10Mb in {@link Context#getCacheDir()}
     */
    @Provides
    @Singleton
    @NonNull
    Cache provideCache(@NonNull Context context) {
        return new Cache(context.getCacheDir(), 10 * 1024 * 1024);
    }

    /**
     * Provides retrofit implementation of {@link OpenWeatherApi}
     *
     * @param baseUrl base URL
     * @param client  http client
     * @return retrofit api implementation
     */
    @Provides
    @Singleton
    @NonNull
    OpenWeatherApi provideWeatherApi(@NonNull @Named(BASE_URL_WEATHER) String baseUrl,
                                     @NonNull OkHttpClient client,
                                     @NonNull Gson gson) {

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(GsonAdapterFactory.create()).create()))
                .build()
                .create(OpenWeatherApi.class);
    }

    @Provides
    @Singleton
    @NonNull
    GooglePlacesApi providePlacesApi(@NonNull @Named(BASE_URL_PLACES) String baseUrl,@NonNull OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(GsonAdapterFactory.create()).create()))
                .client(client)
                .build()
                .create(GooglePlacesApi.class);
    }

    @Provides
    @Singleton
    @NonNull
    public static Gson getGsonWithTypeAdapterFactory(){
        return new GsonBuilder().registerTypeAdapterFactory(GsonAdapterFactory.create()).create();
    }
}
