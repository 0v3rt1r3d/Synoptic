package ru.andrikeev.android.synoptic.model.network.googleplaces;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.andrikeev.android.synoptic.model.data.SuggestionModel;
import ru.andrikeev.android.synoptic.model.network.googleplaces.suggestions.Suggestion;
import ru.andrikeev.android.synoptic.model.network.googleplaces.suggestions.SuggestionsResponse;

/**
 * Created by overtired on 26.07.17.
 */

public class ResponceConverter {
    public static List<SuggestionModel> toViewModel(@NonNull SuggestionsResponse response){
        List<SuggestionModel> predictions = new ArrayList<>();

        for(Suggestion suggestion :response.suggestions()){
            String name = suggestion.description();
            String placeId = suggestion.placeId();

            predictions.add(new SuggestionModel(placeId,name));
        }

        return predictions;
    }
}
