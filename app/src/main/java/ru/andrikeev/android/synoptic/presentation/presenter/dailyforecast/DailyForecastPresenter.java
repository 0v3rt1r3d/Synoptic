package ru.andrikeev.android.synoptic.presentation.presenter.dailyforecast;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.andrikeev.android.synoptic.model.data.DailyForecastModel;
import ru.andrikeev.android.synoptic.model.repository.WeatherRepository;
import ru.andrikeev.android.synoptic.presentation.presenter.RxPresenter;
import ru.andrikeev.android.synoptic.presentation.view.DailyForecastView;

/**
 * Created by overtired on 02.08.17.
 */

@InjectViewState
public class DailyForecastPresenter extends RxPresenter<DailyForecastView> {

    private WeatherRepository repository;

    @Inject
    public DailyForecastPresenter(@NonNull WeatherRepository repository){
        this.repository = repository;
    }

    public void onResume(){
        repository.loadDailyForecast()
                .subscribe(new SingleObserver<DailyForecastModel>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        subscription = disposable;
                    }

                    @Override
                    public void onSuccess(DailyForecastModel model) {
                        getViewState().setDailyForecast(model);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showError();
                    }
                });
    }
}
