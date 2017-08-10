package ru.andrikeev.android.synoptic.presentation.presenter.dailyforecast;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.andrikeev.android.synoptic.model.data.DailyForecastModel;
import ru.andrikeev.android.synoptic.model.repository.Resource;
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
        //todo: leaks
        repository.loadDailyForecast()
                .subscribe(new Observer<Resource<DailyForecastModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //// TODO: 09.08.17 will dispose?
                        subscriptions.add(d);
                    }

                    @Override
                    public void onNext(Resource<DailyForecastModel> resource) {
                        switch (resource.getStatus()){
                            case SUCCESS:
                                getViewState().hideLoading();
                                getViewState().setDailyForecast(resource.getData());
                                break;
                            case ERROR:
                                getViewState().showFetchingError();
                                getViewState().hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showError();
                        getViewState().hideLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void loadDailyForecast(){
        repository.fetchDailyForecast();
    }
}
