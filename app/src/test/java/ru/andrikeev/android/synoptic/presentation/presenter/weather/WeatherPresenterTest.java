package ru.andrikeev.android.synoptic.presentation.presenter.weather;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observable;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.model.persistence.City;
import ru.andrikeev.android.synoptic.model.repository.Resource;
import ru.andrikeev.android.synoptic.model.repository.Status;
import ru.andrikeev.android.synoptic.model.repository.WeatherRepository;
import ru.andrikeev.android.synoptic.presentation.view.WeatherView;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by overtired on 30.07.17.
 */

public class WeatherPresenterTest {

//    private WeatherRepository repository;
//    private WeatherView view;
//    private WeatherPresenter presenter;
//
//    @Before
//    public void prepare() {
//        repository = mock(WeatherRepository.class);
//        view = mock(WeatherView.class);
//
//        presenter = new WeatherPresenter(repository);
//    }
//
//    @Test
//    public void onAttachSuccessFetching() {
//        Resource<WeatherModel> resourceSuccess = mock(Resource.class);
//        WeatherModel model = WeatherModel.Builder()
//                .setClouds("")
//                .setDate("")
//                .setDescription("")
//                .setHumidity("")
//                .setWeatherIconId(0)
//                .setTemperature("")
//                .setPressure("")
//                .setWindSpeed("")
//                .setTemperatureUnits("")
//                .setWindDirectionIconId(0)
//                .build();
//
//        when(resourceSuccess.getStatus()).thenReturn(Status.SUCCESS);
//        when(resourceSuccess.getData()).thenReturn(model);
//
//        when(repository.loadCity()).thenReturn();
//
//        when(repository.loadWeather())
//                .thenReturn(Observable.just(resourceSuccess));
//
//        presenter.attachView(view);
//
//        verify(resourceSuccess, times(1)).getData();
//        verify(repository, times(1)).loadWeather();
//
//        verify(view, times(1)).showLoading();
//        verify(view, times(1)).hideLoading();
//        verify(view, times(1)).setWeather(model);
//        verify(view, times(0)).showFetchingError();
//        verify(view, times(0)).showError();
//    }
//
//    @Test
//    public void onAttachProcessFetching() {
//        Resource<WeatherModel> resourceFetching = mock(Resource.class);
//        WeatherModel model = WeatherModel.Builder()
//                .setHumidity("")
//                .setClouds("")
//                .build();
//
//        when(resourceFetching.getStatus()).thenReturn(Status.FETCHING);
//
//        when(repository.loadWeather())
//                .thenReturn(Observable.just(resourceFetching));
//        when(resourceFetching.getData()).thenReturn(model);
//
//        presenter.attachView(view);
//
//        verify(resourceFetching, times(1)).getData();
//        verify(repository, times(1)).loadWeather();
//
//        verify(view, times(1)).showLoading();
//        verify(view, times(0)).hideLoading();
//        verify(view, times(1)).setWeather(any(WeatherModel.class));
//        verify(view, times(0)).showFetchingError();
//        verify(view, times(0)).showError();
//    }
//
//    @Test
//    public void onAttachErrorFetching() {
//        Resource<WeatherModel> resourceError = mock(Resource.class);
//
//        when(resourceError.getStatus()).thenReturn(Status.ERROR);
//        when(repository.loadWeather())
//                .thenReturn(Observable.just(resourceError));
//
//        presenter.attachView(view);
//
//        verify(resourceError, times(0)).getData();
//        verify(repository, times(1)).loadWeather();
//
//        verify(view, times(1)).showLoading();
//        verify(view, times(1)).hideLoading();
//        verify(view, times(0)).setWeather(any(WeatherModel.class));
//        verify(view, times(1)).showFetchingError();
//        verify(view, times(0)).showError();
//    }

}