package ru.andrikeev.android.synoptic.presentation.view;

import com.arellomobile.mvp.MvpView;

import ru.andrikeev.android.synoptic.model.data.WeatherModel;

/**
 * Current weather screen view.
 */
public interface WeatherView extends MvpView {

    void showLoading();

    void hideLoading();

    void showWeather(WeatherModel model);

    void showError();

    void showFetchingError();
}
