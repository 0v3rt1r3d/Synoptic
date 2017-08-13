package ru.andrikeev.android.synoptic.model.network.googleplaces.places;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by overtired on 26.07.17.
 */

@AutoValue
public abstract class Geometry {
    @SerializedName("location")
    public abstract Location location();

    public static TypeAdapter<Geometry> typeAdapter(Gson gson){
        return new AutoValue_Geometry.GsonTypeAdapter(gson);
    }
}
