package ru.andrikeev.android.synoptic.model.data;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by overtired on 01.08.17.
 */

@AutoValue
public abstract class DailyForecastModel {

    public abstract String cityName();

    public abstract long cityId();

    public abstract float message();

    public abstract List<DailyForecastItem> items();

    public static DailyForecastModel create(
            @NonNull String cityName,
            long cityId,
            long message,
            @NonNull List<DailyForecastItem> items){
        return  new AutoValue_DailyForecastModel(cityName,cityId,message,items);
    }
}
