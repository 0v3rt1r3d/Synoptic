package ru.andrikeev.android.synoptic.model.data;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by overtired on 01.08.17.
 */

@AutoValue
public abstract class ForecastModel {
    public abstract int cityId();
    public abstract String cityName();

    public abstract List<ForecastItem> items();

    public static ForecastModel create(
            int cityId,
            @NonNull String cityName,
            @NonNull List<ForecastItem> items) {
        return new AutoValue_ForecastModel(cityId,cityName,items);
    }
}
