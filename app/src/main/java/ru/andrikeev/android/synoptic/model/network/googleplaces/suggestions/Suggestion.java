package ru.andrikeev.android.synoptic.model.network.googleplaces.suggestions;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by overtired on 26.07.17.
 */

@AutoValue
public abstract class Suggestion {
    @SerializedName("description")
    public abstract String description();
    @SerializedName("place_id")
    public abstract String placeId();

    public static TypeAdapter<Suggestion> typeAdapter(Gson gson){
        return new AutoValue_Suggestion.GsonTypeAdapter(gson);
    }
}
