package ru.andrikeev.android.synoptic.model.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by overtired on 01.08.17.
 */

public class DailyForecastModel {

    public DailyForecastModel(
            @NonNull String cityName,
            long cityId,
            long message,
            @NonNull List<DailyForecastItem> items){
        this.cityName = cityName;
        this.cityId = cityId;
        this.message = message;
        this.items = items;
    }

    private String cityName;

    private long cityId;

    private long message;

    private List<DailyForecastItem> items;
}
