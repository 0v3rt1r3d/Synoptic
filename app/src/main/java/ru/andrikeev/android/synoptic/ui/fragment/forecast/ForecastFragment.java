package ru.andrikeev.android.synoptic.ui.fragment.forecast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.presentation.presenter.forecast.ForecastPresenter;
import ru.andrikeev.android.synoptic.presentation.view.ForecastView;
import ru.andrikeev.android.synoptic.ui.fragment.BaseFragment;

/**
 * Created by overtired on 03.08.17.
 */

public class ForecastFragment extends BaseFragment<ForecastView,ForecastPresenter> implements ForecastView{

    public static final String TAG = "ru.andrikeev.android.synoptic.ui.fragment.forecast.ForecastFragment";

    @Inject
    @InjectPresenter
    ForecastPresenter presenter;

    @Override
    protected int getResourceId() {
        return R.layout.fragment_forecast;
    }

    @Override
    protected ForecastPresenter providePresenter() {
        return presenter;
    }

    public static ForecastFragment create(){
        return new ForecastFragment();
    }
}
