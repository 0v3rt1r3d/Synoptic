package ru.andrikeev.android.synoptic.model.network.googleplaces;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import ru.andrikeev.android.synoptic.model.network.googleplaces.places.PlacesResponse;
import ru.andrikeev.android.synoptic.model.network.googleplaces.suggestions.SuggestionsResponse;

/**
 * Created by overtired on 08.08.17.
 */

public interface GooglePlacesService {

    Single<PlacesResponse> loadPlace(String placeId);
    Single<SuggestionsResponse> loadPredictions(String input);
}
