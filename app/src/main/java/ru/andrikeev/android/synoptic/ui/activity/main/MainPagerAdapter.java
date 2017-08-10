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

    private List<Fragment> fragments;

    public void setFragments(@NonNull List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
