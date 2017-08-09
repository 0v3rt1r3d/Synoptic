package ru.andrikeev.android.synoptic.presentation.presenter.weather;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.data.ForecastModel;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.model.repository.Resource;
import ru.andrikeev.android.synoptic.model.repository.WeatherRepository;
import ru.andrikeev.android.synoptic.presentation.presenter.RxPresenter;
import ru.andrikeev.android.synoptic.presentation.view.WeatherView;
import timber.log.Timber;

/**
 * Presenter for {@link WeatherView} with
 * {@link com.arellomobile.mvp.viewstate.MvpViewState} for saving the view state.
 */
@InjectViewState
public class WeatherPresenter extends RxPresenter<WeatherView> {

    private Disposable forecastsSubscription;

    private WeatherRepository repository;

    @Inject
    WeatherPresenter(@NonNull WeatherRepository repository) {
        this.repository = repository;
    }

    private void loadWeather() {
        getViewState().showLoading();
        repository.loadWeather()
                .subscribe(
                        new Observer<Resource<WeatherModel>>() {
                            @Override
                            public void onSubscribe(Disposable disposable) {
                                subscriptions.add(disposable);
                            }

                            @Override
                            public void onNext(Resource<WeatherModel> resource) {
                                switch (resource.getStatus()) {
                                    case SUCCESS:
                                        getViewState().hideLoading();
                                        getViewState().showWeather(resource.getData());
                                        loadCity();
                                        break;
                                    case FETCHING:
                                        getViewState().showWeather(resource.getData());
                                        break;
                                    case ERROR:
                                        getViewState().showFetchingError();
                                        getViewState().hideLoading();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                getViewState().hideLoading();
                                getViewState().showError();
                            }

                            @Override
                            public void onComplete() {
                            }
                        }
                );
    }

    public void fetchWeather() {
        getViewState().showLoading();
        repository.fetchWeather();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadWeather();
    }

//    public void fetchDailyForecast(){
//        repository.fetchDailyForecast();
//        repository.loadForecasts()
//                .subscribe(new SingleObserver<ForecastModel>(){
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onSuccess(ForecastModel forecastModel) {
//                        Timber.d("Forecast was restored from storage %s",forecastModel.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.d("Forecast restoring failed");
//                    }
//                });
//    }

    public void fetchForecast(){
        repository.fetchForecast();
    }

    private void loadCity(){
        repository.loadCity().subscribe(name -> getViewState().setCity(name));
    }

    public void onResume(){
        repository.loadForecasts()
                .subscribe(new Observer<Resource<ForecastModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        forecastsSubscription = d;
                    }

                    @Override
                    public void onNext(Resource<ForecastModel> forecastModelResource) {
                        switch (forecastModelResource.getStatus()){
                            case SUCCESS:
                                getViewState().hideLoading();
                                getViewState().setForecast(forecastModelResource.getData());//todo why?
                                break;
                            case ERROR:
                                getViewState().hideLoading();
                                getViewState().showFetchingError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().hideLoading();
                        getViewState().showError();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void updateData(){
        fetchWeather();
        fetchForecast();
    }
}
