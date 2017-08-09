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
import ru.andrikeev.android.synoptic.model.repository.WeatherRepository;
import ru.andrikeev.android.synoptic.presentation.presenter.RxPresenter;
import ru.andrikeev.android.synoptic.presentation.view.CityView;

/**
 * Created by overtired on 25.07.17.
 */

@InjectViewState
public class CityPresenter extends RxPresenter<CityView> {

    private WeatherRepository repository;

    private Settings settings;

    private Disposable textChangedSubscription;

    @Inject
    public CityPresenter(@NonNull WeatherRepository repository,
                         @NonNull Settings settings) {
        this.repository = repository;
        this.settings = settings;
    }

    public void loadCity(final String placeId) {
        getViewState().showLoading();
        getViewState().hideKeyboard();

//        subscription = repository.fetchCity(placeId);
//                .subscribe(cityId -> {
//                    settings.setCityId(cityId);
//                    getViewState().hideProgressAndExit();
//                }, throwable -> {
//                    getViewState().showError();
//                    getViewState().hideLoading();
//                });
    }

    public void onTextChanged(Observable<CharSequence> observable) {
        //// TODO: 08.08.17  leeks

        textChangedSubscription = observable
                .debounce(400, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .filter(charSequence -> charSequence.length() > 0)
                .map(CharSequence::toString)
                .subscribe(input -> repository.fetchPredictions(input)
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
