package ru.andrikeev.android.synoptic.presentation.presenter.dailyforecast;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import ru.andrikeev.android.synoptic.presentation.presenter.RxPresenter;
import ru.andrikeev.android.synoptic.presentation.view.DailyForecastView;

/**
 * Created by overtired on 02.08.17.
 */

@InjectViewState
public class DailyForecastPresenter extends RxPresenter<DailyForecastView> {

    @Inject
    public DailyForecastPresenter(){
    }

    public void onResume(){
        //getViewState().updateList();
    }
}
