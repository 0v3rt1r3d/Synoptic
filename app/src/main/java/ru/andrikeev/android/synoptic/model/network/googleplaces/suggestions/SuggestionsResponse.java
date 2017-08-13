package ru.andrikeev.android.synoptic.model.network.googleplaces.suggestions;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by overtired on 26.07.17.
 */

@AutoValue
public abstract class SuggestionsResponse {
    @SerializedName("predictions")
    public abstract List<Suggestion> suggestions();

    public static TypeAdapter<SuggestionsResponse> typeAdapter(Gson gson){
        return new AutoValue_SuggestionsResponse.GsonTypeAdapter(gson);
    }
}
