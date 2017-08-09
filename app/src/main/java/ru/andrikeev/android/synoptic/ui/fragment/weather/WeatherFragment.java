package ru.andrikeev.android.synoptic.ui.fragment.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.model.data.ForecastModel;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.presentation.presenter.weather.WeatherPresenter;
import ru.andrikeev.android.synoptic.presentation.view.WeatherView;
import ru.andrikeev.android.synoptic.ui.fragment.BaseFragment;

public class WeatherFragment extends BaseFragment<WeatherView, WeatherPresenter> implements WeatherView {

    public static final String TAG = "ru.andrikeev.android.synoptic.ui.fragment.weather.WeatherFragment";

    private ForecastAdapter adapter;

    private RecyclerView recycler;
    private SwipeRefreshLayout refreshLayout;


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

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> presenter.updateData());

        adapter = new ForecastAdapter();
        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
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
        adapter.setWeather(model);
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

    @Override
    public void setForecast(@NonNull ForecastModel forecast) {
        adapter.setForecast(forecast);
        adapter.notifyItemRangeChanged(0, forecast.items().size());
    }

    public static WeatherFragment create() {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
