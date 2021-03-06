package ru.andrikeev.android.synoptic.model.network.googleplaces;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.andrikeev.android.synoptic.model.network.googleplaces.places.PlacesResponse;
import ru.andrikeev.android.synoptic.model.network.googleplaces.suggestions.SuggestionsResponse;

/**
 * Created by overtired on 25.07.17.
 */

public interface GooglePlacesApi {
    String STATUS_OK = "OK";

    String API_KEY = "key";
    String LANGUAGE = "language";
    String INPUT = "input";
    String PLACE_ID = "placeid";

    @GET("details/json?")
    Single<PlacesResponse> getPlacesByQuery(
            @Query(API_KEY) String key,
            @Query(PLACE_ID) String placeId,
            @Query(LANGUAGE) String language);

    @GET("autocomplete/json?type=(cities)")
    Single<SuggestionsResponse> getPredictions(
            @Query(API_KEY) String key,
            @Query(INPUT) String input,
            @Query(LANGUAGE) String language);
}
