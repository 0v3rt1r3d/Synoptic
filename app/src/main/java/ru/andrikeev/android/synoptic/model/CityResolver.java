package ru.andrikeev.android.synoptic.model;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.andrikeev.android.synoptic.model.data.SuggestionModel;
import ru.andrikeev.android.synoptic.model.network.googleplaces.GooglePlacesApi;
import ru.andrikeev.android.synoptic.model.network.googleplaces.GooglePlacesService;
import ru.andrikeev.android.synoptic.model.network.googleplaces.ResponceConverter;
import ru.andrikeev.android.synoptic.model.network.openweather.OpenWeatherService;
import ru.andrikeev.android.synoptic.model.persistence.CacheService;
import ru.andrikeev.android.synoptic.model.persistence.City;
import timber.log.Timber;

/**
 * Created by overtired on 28.07.17.
 */

public class CityResolver {
    private CacheService cacheService;
    private GooglePlacesService placesService;
    private OpenWeatherService weatherService;
    private ModelsConverter converter;

    @Inject
    public CityResolver(@NonNull GooglePlacesService placesService,
                        @NonNull OpenWeatherService weatherService,
                        @NonNull ModelsConverter converter,
                        @NonNull CacheService cacheService) {
        this.placesService = placesService;
        this.weatherService = weatherService;
        this.converter = converter;
        this.cacheService = cacheService;
    }

    public Single<List<SuggestionModel>> loadPredictions(@NonNull String input) {
        return placesService.loadPredictions(input)
                .map(ResponceConverter::toViewModel);
    }

    public Single<Long> loadCityId(@NonNull String placeId) {
        //todo: add city name from other response
        return placesService.loadPlace(placeId)
                .map(placesResponse -> {
                    Timber.d("Status", placesResponse.status());
                    if (placesResponse.status().equals(GooglePlacesApi.STATUS_OK)) {
                        return placesResponse.resultPlace().geometry().location();
                    } else {
                        throw new Exception("Couldn't load this city");
                    }
                })
                .flatMap(location -> weatherService.getWeather(location.latitude(), location.longitude()))
                .map(weatherResponse -> converter.toCacheModel(weatherResponse))
                .map(weather -> {
                    //todo: cache city
                    City city = City.builder()
                            .setCityId(weather.cityId())
                            .setCityName("City!!!")
                            .setLastMessage(City.NULL_MESSAGE)
                            .build();
                    cacheService.cacheCity(city);
                    return weather.cityId();
                });
    }
}
