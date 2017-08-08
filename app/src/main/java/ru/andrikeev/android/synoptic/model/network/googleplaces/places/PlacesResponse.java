package ru.andrikeev.android.synoptic.model.network.googleplaces.places;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by overtired on 25.07.17.
 */

@AutoValue
public abstract class PlacesResponse {

    @SerializedName("status")
    public abstract String status();

    @SerializedName("result")
    public abstract Result resultPlace();

    public static TypeAdapter<PlacesResponse> typeAdapter(Gson gson){
        return new AutoValue_PlacesResponse.GsonTypeAdapter(gson);
    }
}
