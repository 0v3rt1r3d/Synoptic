package ru.andrikeev.android.synoptic.presentation.view;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

import ru.andrikeev.android.synoptic.model.data.DailyForecastModel;

/**
 * Created by overtired on 02.08.17.
 */

public interface DailyForecastView extends MvpView {
    void updateList(@NonNull DailyForecastModel model);
}
