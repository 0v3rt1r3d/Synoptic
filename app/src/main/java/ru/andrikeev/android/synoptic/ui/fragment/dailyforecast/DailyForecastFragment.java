package ru.andrikeev.android.synoptic.ui.fragment.dailyforecast;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

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

    private DailyForecastAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_daily_forecast);
    }

    @Override
    public void updateList(@NonNull DailyForecastModel model){
        adapter.clear();
        adapter.add(model.items());
        adapter.notifyItemRangeChanged(0,model.items().size());
    }

    @Inject
    @InjectPresenter
    DailyForecastPresenter presenter;

    @Override
    protected int getResourceId() {
        return R.layout.fragment_dailyforecast;
    }

    @Override
    protected DailyForecastPresenter providePresenter() {
        return presenter;
    }

    public static DailyForecastFragment create(){
        return new DailyForecastFragment();
    }
}
