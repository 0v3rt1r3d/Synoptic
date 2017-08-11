package ru.andrikeev.android.synoptic.presentation.presenter.city;


import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.persistence.City;
import ru.andrikeev.android.synoptic.model.repository.WeatherRepository;
import ru.andrikeev.android.synoptic.presentation.presenter.RxPresenter;
import ru.andrikeev.android.synoptic.presentation.view.CityView;
import timber.log.Timber;

/**
 * Created by overtired on 25.07.17.
 */

@InjectViewState
public class CityPresenter extends RxPresenter<CityView> {

    private WeatherRepository repository;

    private Settings settings;

    @Inject
    public CityPresenter(@NonNull WeatherRepository repository,
                         @NonNull Settings settings) {
        this.repository = repository;
        this.settings = settings;
    }

    public void loadCity(final String placeId) {
        getViewState().showLoading();
        getViewState().hideKeyboard();

        Disposable subscription = repository.fetchCity(placeId)
                .subscribe(cityId -> {
                    settings.setCityId(cityId);
                    settings.setFirstStart(false);
                    getViewState().hideProgressAndExit();
                }, throwable -> {
                    getViewState().showError();
                    getViewState().hideLoading();
                });

        subscriptions.add(subscription);
    }

    public void onCityRemoved(@NonNull City city) {
        repository.removeCachedCity(city)
                .subscribe(city1 -> getViewState().showCityRemoved(city),
                        throwable -> getViewState().showError()
                );
    }

    public void onTextChanged(Observable<String> observable) {
        subscriptions.add(observable.subscribe(input -> {
            if (input.length() > 0) {
                repository.fetchPredictions(input)
                        .subscribe(suggestionModels -> getViewState().setSuggestions(suggestionModels),
                                throwable -> getViewState().showError());
            } else {
                repository.loadCachedCities()
                        .subscribe(cities -> getViewState().setCities(cities),
                                throwable -> getViewState().showError());
            }
        }, throwable -> Timber.d(throwable, "Could not load cities")));
    }

    public void onCitySelected(@NonNull City city) {
        settings.setCityId(city.cityId());
        settings.setFirstStart(false);
        getViewState().hideProgressAndExit();
    }
}
