package ru.andrikeev.android.synoptic.ui.fragment.forecast;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.model.data.ForecastModel;
import ru.andrikeev.android.synoptic.presentation.presenter.forecast.ForecastPresenter;
import ru.andrikeev.android.synoptic.presentation.view.ForecastView;
import ru.andrikeev.android.synoptic.ui.fragment.BaseFragment;

/**
 * Created by overtired on 03.08.17.
 */

public class ForecastFragment extends BaseFragment<ForecastView,ForecastPresenter> implements ForecastView{

    public static final String TAG = "ru.andrikeev.android.synoptic.ui.fragment.forecast.ForecastFragment";

    private RecyclerView recycler;
    private ForecastAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    //todo: progressbar

    @Inject
    @InjectPresenter
    ForecastPresenter presenter;

    @Override
    @ProvidePresenter
    protected ForecastPresenter providePresenter() {
        return presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ForecastAdapter();
        recycler = view.findViewById(R.id.forecastRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnClickListener(v->{
            //todo: update
        });
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_forecast;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    public static ForecastFragment create(){
        return new ForecastFragment();
    }

    @Override
    public void setForecast(@NonNull ForecastModel forecast) {
        adapter.setForecast(forecast);
        //todo:
        //adapter.notifyItemRangeChanged(0,forecast.items().size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(),"Error!!!",Toast.LENGTH_SHORT).show();
    }
}
