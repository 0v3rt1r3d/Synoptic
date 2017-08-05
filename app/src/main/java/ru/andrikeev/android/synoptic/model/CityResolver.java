package ru.andrikeev.android.synoptic.model;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.andrikeev.android.synoptic.model.data.SuggestionModel;
import ru.andrikeev.android.synoptic.model.network.googleplaces.GooglePlacesApi;
import ru.andrikeev.android.synoptic.model.network.googleplaces.GooglePlacesService;
import ru.andrikeev.android.synoptic.model.network.googleplaces.ResponceConverter;
import ru.andrikeev.android.synoptic.model.network.googleplaces.places.Location;
import ru.andrikeev.android.synoptic.model.network.googleplaces.places.PlacesResponse;
import ru.andrikeev.android.synoptic.model.network.openweather.OpenWeatherService;
import ru.andrikeev.android.synoptic.model.network.openweather.response.weather.WeatherResponse;
import ru.andrikeev.android.synoptic.model.persistence.Weather;
import timber.log.Timber;

/**
 * Created by overtired on 28.07.17.
 */

public class CityResolver {
    private GooglePlacesService placesService;
    private OpenWeatherService weatherService;
    private ModelsConverter converter;

    @Inject
    public CityResolver(@NonNull GooglePlacesService placesService,
                        @NonNull OpenWeatherService weatherService,
                        @NonNull ModelsConverter converter){
        this.placesService = placesService;
        this.weatherService = weatherService;
        this.converter = converter;
    }

    public Single<List<SuggestionModel>> loadPredictions(@NonNull String input){
        return placesService.loadPredictions(input)
                .subscribeOn(Schedulers.io())
                .map(ResponceConverter::toViewModel);
    }

    public Single<Long> loadCityId(String placeId){
        return placesService.loadPlace(placeId)
                .map(placesResponse -> {
                    Timber.d("Status",placesResponse.getStatus());
                    if(placesResponse.getStatus().equals(GooglePlacesApi.STATUS_OK)) {
                        return placesResponse.getResultPlace().getGeometry().getLocation();
                    }else {
                        throw new Exception("Couldn't load this city");
                    }
                })
                //todo: lambda?
                .flatMap(new Function<Location, SingleSource<WeatherResponse>>() {
                    @Override
                    public SingleSource<WeatherResponse> apply(@NonNull Location location) throws Exception {
                        return weatherService.getWeather(location.getLatitude(), location.getLongitude());
                    }
                })
                .map(weatherResponse -> converter.toCacheModel(weatherResponse))
                .map(weather -> weather.getCityId());
    }
}
