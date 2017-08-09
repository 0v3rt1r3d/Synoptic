package ru.andrikeev.android.synoptic.ui.fragment.dailyforecast;

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
import ru.andrikeev.android.synoptic.model.data.DailyForecastModel;
import ru.andrikeev.android.synoptic.presentation.presenter.dailyforecast.DailyForecastPresenter;
import ru.andrikeev.android.synoptic.presentation.view.DailyForecastView;
import ru.andrikeev.android.synoptic.ui.fragment.BaseFragment;

/**
 * Created by overtired on 02.08.17.
 */

public class DailyForecastFragment extends BaseFragment<DailyForecastView, DailyForecastPresenter>
        implements DailyForecastView{
    public static final String TAG = "ru.andrikeev.android.synoptic.ui.fragment.dailyforecast.DailyForecastFragment";

    private SwipeRefreshLayout refreshLayout;
    private DailyForecastAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new DailyForecastAdapter();

        recyclerView = view.findViewById(R.id.recyclerDailyForecast);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(()->{
            refreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void setDailyForecast(@NonNull DailyForecastModel model){
        adapter.setDailyForecast(model);
        adapter.notifyDataSetChanged();
        adapter.notifyItemRangeChanged(0,model.items().size());
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(),"Error!",Toast.LENGTH_SHORT).show();
    }

    @Inject
    @InjectPresenter
    DailyForecastPresenter presenter;

    @Override
    protected int getResourceId() {
        return R.layout.fragment_dailyforecast;
    }

    @Override
    @ProvidePresenter
    protected DailyForecastPresenter providePresenter() {
        return presenter;
    }

    public static DailyForecastFragment create(){
        return new DailyForecastFragment();
    }
}
