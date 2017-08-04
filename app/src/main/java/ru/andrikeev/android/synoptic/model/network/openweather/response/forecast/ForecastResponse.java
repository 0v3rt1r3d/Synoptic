package ru.andrikeev.android.synoptic.model.network.openweather.response.forecast;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.andrikeev.android.synoptic.model.network.openweather.response.common.City;

/**
 * Created by overtired on 01.08.17.
 */

@AutoValue
public abstract class ForecastResponse {
    @SerializedName("city")
    public abstract City city();

    @SerializedName("message")
    public abstract float message();

    @SerializedName("list")
    public abstract List<Forecast> forecastsList();

    public static TypeAdapter<ForecastResponse> typeAdapter(Gson gson){
        return new AutoValue_ForecastResponse.GsonTypeAdapter(gson);
    }
}
