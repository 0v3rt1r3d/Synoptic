package ru.andrikeev.android.synoptic.presentation.presenter.city;


import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.CityResolver;
import ru.andrikeev.android.synoptic.model.data.SuggestionModel;
import ru.andrikeev.android.synoptic.presentation.presenter.RxPresenter;
import ru.andrikeev.android.synoptic.presentation.view.CityView;

/**
 * Created by overtired on 25.07.17.
 */

@InjectViewState
public class CityPresenter extends RxPresenter<CityView> {

    private Settings settings;

    private CityResolver cityResolver;

    private Disposable textChangedSubscription;

    @Inject
    public CityPresenter(@NonNull CityResolver cityResolver,
                         @NonNull Settings settings) {
        this.cityResolver = cityResolver;
        this.settings = settings;
    }

    public void loadCity(final String placeId) {
        getViewState().showLoading();
        getViewState().hideKeyboard();

        subscription = cityResolver.loadCityId(placeId)
                .subscribe(cityId -> {
                    settings.setCityId(cityId);
                    getViewState().hideProgressAndExit();
                }, throwable -> {
                    getViewState().showError();
                    getViewState().hideLoading();
                });
    }

    public void onTextChanged(Observable<CharSequence> observable) {
        textChangedSubscription = observable
                .debounce(400, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .filter(charSequence -> charSequence.length() > 0)
                .map(charSequence -> charSequence.toString())
                .subscribe(input -> cityResolver.loadPredictions(input)
                        .subscribe(suggestionModels -> getViewState().updateList(suggestionModels),
                                throwable -> getViewState().showError()));
    }

    public void onDestroyView() {
        if (textChangedSubscription != null) {
            textChangedSubscription.dispose();
            textChangedSubscription = null;
        }
    }
}
