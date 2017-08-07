package ru.andrikeev.android.synoptic.presentation.view;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

import ru.andrikeev.android.synoptic.model.data.ForecastModel;

/**
 * Created by overtired on 03.08.17.
 */

public interface ForecastView extends MvpView {
    void setForecast(@NonNull ForecastModel forecast);
    void showError();
}
