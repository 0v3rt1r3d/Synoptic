package ru.andrikeev.android.synoptic.presentation.presenter.forecast;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.andrikeev.android.synoptic.model.data.ForecastModel;
import ru.andrikeev.android.synoptic.model.repository.WeatherRepository;
import ru.andrikeev.android.synoptic.presentation.presenter.RxPresenter;
import ru.andrikeev.android.synoptic.presentation.view.ForecastView;
import ru.andrikeev.android.synoptic.ui.fragment.forecast.ForecastFragment;

/**
 * Created by overtired on 03.08.17.
 */

@InjectViewState
public class ForecastPresenter extends RxPresenter<ForecastView>{
    private WeatherRepository repository;

    @Inject
    ForecastPresenter(@NonNull WeatherRepository repository){
        this.repository = repository;
    }

    public void onResume(){
        repository.loadForecasts()
                .subscribe(new SingleObserver<ForecastModel>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        subscription = disposable;
                    }

                    @Override
                    public void onSuccess(ForecastModel forecastModel) {
                        getViewState().setForecast(forecastModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showError();
                    }
                });
    }

    public void loadForecast(){

    }
}
