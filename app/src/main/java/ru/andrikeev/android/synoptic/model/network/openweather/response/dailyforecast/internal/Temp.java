package ru.andrikeev.android.synoptic.model.network.openweather.response.dailyforecast.internal;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by overtired on 01.08.17.
 */

@AutoValue
public abstract class Temp {
    @SerializedName("day")
    public abstract float tempDay();

    @SerializedName("night")
    public abstract float tempNight();

    @SerializedName("eve")
    public abstract float tempEvening();

    @SerializedName("morn")
    public abstract float tempMorning();
    public static TypeAdapter<Temp> typeAdapter(Gson gson){
        return new AutoValue_Temp.GsonTypeAdapter(gson);
    }

}
