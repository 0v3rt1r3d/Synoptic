package ru.andrikeev.android.synoptic.ui.activity.main;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.andrikeev.android.synoptic.ui.fragment.dailyforecast.DailyForecastFragment;
import ru.andrikeev.android.synoptic.ui.fragment.weather.WeatherFragment;

/**
 * Created by overtired on 09.08.17.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return WeatherFragment.create();
            case 1:
                return DailyForecastFragment.create();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
