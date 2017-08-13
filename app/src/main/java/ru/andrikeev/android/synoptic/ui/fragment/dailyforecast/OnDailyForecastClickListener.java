package ru.andrikeev.android.synoptic.ui.fragment.dailyforecast;

import android.support.annotation.NonNull;

import ru.andrikeev.android.synoptic.model.data.DailyForecastModel;
import ru.andrikeev.android.synoptic.model.persistence.DailyForecast;

/**
 * Created by overtired on 03.08.17.
 */

public interface OnDailyForecastClickListener {
    void onDailyForecastClick(@NonNull DailyForecastModel mode);
}
