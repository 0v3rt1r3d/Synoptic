package ru.andrikeev.android.synoptic.model.network.googleplaces.places;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by overtired on 26.07.17.
 */

@AutoValue
public abstract class Result {
    @SerializedName("formatted_address")
    public abstract String address();

    @SerializedName("geometry")
    public abstract Geometry geometry();

    public static TypeAdapter<Result> typeAdapter(Gson gson){
        return new AutoValue_Result.GsonTypeAdapter(gson);
    }
}
