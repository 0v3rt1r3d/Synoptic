package ru.andrikeev.android.synoptic.model.data;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by overtired on 01.08.17.
 */

public class ForecastModel {

    private int cityId;
    private String cityName;

    private List<ForecastItem> items;

    public ForecastModel(
            int cityId,
            @NonNull String cityName,
            @NonNull List<ForecastItem> items) {
        this.cityName = cityName;
        this.cityId = cityId;
        this.items = items;
    }
}
