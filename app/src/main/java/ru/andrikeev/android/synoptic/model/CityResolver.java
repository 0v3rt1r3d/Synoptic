package ru.andrikeev.android.synoptic.model;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.andrikeev.android.synoptic.model.data.SuggestionModel;
import ru.andrikeev.android.synoptic.model.network.googleplaces.GooglePlacesServiceImpl;
import ru.andrikeev.android.synoptic.model.network.googleplaces.ResponceConverter;
import ru.andrikeev.android.synoptic.model.network.openweather.OpenWeatherServiceImpl;
import ru.andrikeev.android.synoptic.model.persistence.CacheService;

/**
 * Created by overtired on 28.07.17.
 */

public class CityResolver {
    private CacheService cacheService;
    private GooglePlacesServiceImpl placesService;
    private OpenWeatherServiceImpl weatherService;
    private ModelsConverter converter;

    @Inject
    public CityResolver(@NonNull GooglePlacesServiceImpl placesService,
                        @NonNull OpenWeatherServiceImpl weatherService,
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

//    public Single<Long> loadCityId(@NonNull String placeId) {
//        //todo: add city name from other response
//        return placesService.loadPlace(placeId)
//                .map(placesResponse -> {
//                    Timber.d("Status", placesResponse.status());
//                    if (placesResponse.status().equals(GooglePlacesApi.STATUS_OK)) {
//                        Location location = placesResponse.resultPlace().geometry().location();
//                        String cityName = placesResponse.resultPlace().address();
//                        return new Pair<>(location,cityName);
//                    } else {
//                        throw new Exception("Couldn't load this city");
//                    }
//                })
//                .map(locationStringPair -> new Pair<>(weatherService.getWeather(locationStringPair.first.latitude(),
//                        locationStringPair.first.longitude()),
//                        locationStringPair.second))
//                .flatMap(singleStringPair -> {
//                    singleStringPair.first
//                            .map(weatherResponse -> {
//                                return converter.toCacheModel(weatherResponse);
//                            })
//                            .doOnSuccess(weather -> {
//                        City city = City.builder()
//                                .setLastMessage(City.NULL_MESSAGE)
//                                .setCityId(weather.cityId())
//                                .setCityName(singleStringPair.second)
//                                .build();
//                        cacheService.cacheCity(city);
//                    });
//                });
//    }
}
