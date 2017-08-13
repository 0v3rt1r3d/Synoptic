package ru.andrikeev.android.synoptic.presentation.presenter.city;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Single;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.data.SuggestionModel;
import ru.andrikeev.android.synoptic.model.persistence.City;
import ru.andrikeev.android.synoptic.model.repository.WeatherRepository;
import ru.andrikeev.android.synoptic.presentation.view.CityView;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by overtired on 13.08.17.
 */
@RunWith(JUnitParamsRunner.class)
public class CityPresenterTest {
    private WeatherRepository repository;
    private Settings settings;
    private CityView view;

    private CityPresenter presenter;

    private City city = City.builder()
            .setCityId(12345)
            .setCityName("Moscow")
            .setLastMessage(0.0f)
            .build();

    private List<City> cities = new ArrayList<>();

    @Before
    public void prepare(){
        repository = mock(WeatherRepository.class);
        settings = mock(Settings.class);
        view = mock(CityView.class);
        presenter = new CityPresenter(repository,settings);
        presenter.attachView(view);
    }

    @Test
    public void onCityRemoved(){

        when(settings.getCityId()).thenReturn(54321L);
        when(repository.loadCachedCities()).thenReturn(Single.just(cities));
        when(repository.removeCachedCity(city)).thenReturn(Single.just(city));

        presenter.onCityRemoved(city);

        verify(settings,times(0)).setFirstStart(true);
        verify(view,times(1)).showCityRemoved(city);
        verify(view,times(1)).setCities(cities);
        verify(view,times(0)).showError();
    }

    @Test
    public void onOnlyCityRemoved(){
        City city = City.builder()
                .setCityId(12345)
                .setCityName("Moscow")
                .setLastMessage(0.0f)
                .build();

        List<City> cities = new ArrayList<>();

        when(settings.getCityId()).thenReturn(city.cityId());
        when(repository.loadCachedCities()).thenReturn(Single.just(cities));
        when(repository.removeCachedCity(city)).thenReturn(Single.just(city));

        presenter.attachView(view);
        presenter.onCityRemoved(city);

        verify(settings,times(1)).setFirstStart(true);
        verify(view,times(1)).showCityRemoved(city);
        verify(view,times(1)).setCities(cities);
        verify(view,times(0)).showError();
    }

    @Test
    public void loadCity(){
        String mokedPlaceId = "placeId";

        when(settings.getCityId()).thenReturn(city.cityId());
        when(repository.fetchCity(mokedPlaceId)).thenReturn(Single.just(city.cityId()));

        presenter.loadCity(mokedPlaceId);

        verify(view,times(1)).showLoading();
        verify(view,times(1)).hideKeyboard();
        verify(settings,times(1)).setCityId(city.cityId());
        verify(settings,times(1)).setFirstStart(false);
        verify(view,times(1)).hideProgressAndExit();
        verify(view,times(0)).showError();
        verify(view,times(0)).hideLoading();
    }

    @Test
    public void onCitySelected(){
        presenter.onCitySelected(city);

        verify(settings,times(1)).setCityId(city.cityId());
        verify(settings,times(1)).setFirstStart(false);
        verify(view,times(1)).hideProgressAndExit();
    }

    @Test
    @Parameters({"Moscow","Москва"})
    public void onTextChanged(String text){
        List<SuggestionModel> suggestions = new ArrayList<>();
        when(repository.fetchPredictions(text)).thenReturn(Single.just(suggestions));

        presenter.onTextChanged(Observable.just(text));

        verify(view,times(1)).setSuggestions(suggestions);
        verify(view,times(0)).showError();
    }

    @Test
    public void onEmptyTextChanged(){
        List<City> cities = new ArrayList<>();
        when(repository.loadCachedCities()).thenReturn(Single.just(cities));

        presenter.onTextChanged(Observable.just(""));

        verify(view,times(1)).setCities(cities);
        verify(view,times(0)).showError();
    }
}