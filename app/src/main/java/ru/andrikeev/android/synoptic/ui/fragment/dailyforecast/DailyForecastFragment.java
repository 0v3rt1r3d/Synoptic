package ru.andrikeev.android.synoptic.ui.fragment.dailyforecast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.presentation.presenter.dailyforecast.DailyForecastPresenter;
import ru.andrikeev.android.synoptic.presentation.view.DailyForecastView;
import ru.andrikeev.android.synoptic.ui.fragment.BaseFragment;

/**
 * Created by overtired on 02.08.17.
 */

public class DailyForecastFragment extends BaseFragment<DailyForecastView, DailyForecastPresenter>
        implements DailyForecastView{
    public static final String TAG = "ru.andrikeev.android.synoptic.ui.fragment.dailyforecast.DailyForecastFragment";

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
