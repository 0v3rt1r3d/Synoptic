package ru.andrikeev.android.synoptic.presentation.presenter.forecast;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import ru.andrikeev.android.synoptic.presentation.presenter.RxPresenter;
import ru.andrikeev.android.synoptic.presentation.view.ForecastView;
import ru.andrikeev.android.synoptic.ui.fragment.forecast.ForecastFragment;

/**
 * Created by overtired on 03.08.17.
 */

@InjectViewState
public class ForecastPresenter extends RxPresenter<ForecastView>{
    @Inject
    public ForecastPresenter(){

    }
}
