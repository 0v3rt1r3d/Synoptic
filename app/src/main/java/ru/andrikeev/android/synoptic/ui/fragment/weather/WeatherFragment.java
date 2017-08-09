package ru.andrikeev.android.synoptic.ui.fragment.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.presentation.presenter.weather.WeatherPresenter;
import ru.andrikeev.android.synoptic.presentation.view.WeatherView;
import ru.andrikeev.android.synoptic.ui.activity.city.CityActivity;
import ru.andrikeev.android.synoptic.ui.fragment.BaseFragment;
import ru.andrikeev.android.synoptic.utils.IntentHelper;

public class WeatherFragment extends BaseFragment<WeatherView, WeatherPresenter> implements WeatherView {

    public static final String TAG = "ru.andrikeev.android.synoptic.ui.fragment.weather.WeatherFragment";

    private SwipeRefreshLayout refreshLayout;
    private TextView lastUpdate;
    private ImageView weatherIcon;
    private TextView temperature;
    private TextView temperatureUnits;
    private TextView description;
    private TextView pressure;
    private TextView humidity;
    private TextView wind;
    private ImageView windDirection;
    private TextView clouds;

    @Inject
    @InjectPresenter
    WeatherPresenter presenter;

    @ProvidePresenter
    protected WeatherPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_weather;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);


        lastUpdate = view.findViewById(R.id.lastUpdate);
        weatherIcon = view.findViewById(R.id.weatherIcon);
        temperature = view.findViewById(R.id.temperature);
        temperatureUnits = view.findViewById(R.id.temperatureUnits);
        description = view.findViewById(R.id.description);
        pressure = view.findViewById(R.id.pressure);
        humidity = view.findViewById(R.id.humidity);
        wind = view.findViewById(R.id.wind);
        windDirection = view.findViewById(R.id.windDirection);
        clouds = view.findViewById(R.id.clouds);

        //todo:remove the listener
        weatherIcon.setOnClickListener((View v)->{
            presenter.fetchForecast();
            presenter.fetchDailyForecast();
        });

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> presenter.fetchWeather());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showLoading() {
        if (!refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showWeather(WeatherModel model) {
        lastUpdate.setText(model.date());
        weatherIcon.setImageResource(model.weatherIconId());
        temperature.setText(model.temperature());
        temperatureUnits.setText(model.temperatureUnits());
        description.setText(model.description());
        pressure.setText(model.pressure());
        humidity.setText(model.humidity());
        wind.setText(model.windSpeed());
        windDirection.setImageResource(model.windDirectionIconId());
        clouds.setText(model.clouds());
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show(); // TODO: show error
    }

    @Override
    public void showFetchingError() {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show(); // TODO: show error
    }

    @Override
    public void setCity(@NonNull String city) {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(city);
    }

    public static WeatherFragment create() {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
