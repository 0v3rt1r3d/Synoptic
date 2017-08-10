package ru.andrikeev.android.synoptic.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.ui.activity.BaseActivity;
import ru.andrikeev.android.synoptic.ui.fragment.dailyforecast.DailyForecastFragment;
import ru.andrikeev.android.synoptic.ui.fragment.weather.WeatherFragment;
import ru.andrikeev.android.synoptic.utils.IntentHelper;

/**
 * Главный экран приложения.
 */
public class MainActivity extends BaseActivity
        implements HasSupportFragmentInjector {

    private MainPagerAdapter adapter;
    private ViewPager viewPager;
    private DotsIndicator dots;

    @Inject
    protected DispatchingAndroidInjector<Fragment> injector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return injector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (settings.isFirstStart()) {
            IntentHelper.openCityActivity(this, true);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.mainViewPager);

        if (viewPager != null) {
            adapter = new MainPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);

            dots= (DotsIndicator) findViewById(R.id.dots_indicator);
            dots.setViewPager(viewPager);
        }

//        if (savedInstanceState == null) {
//            WeatherFragment weatherFragment = WeatherFragment.create();
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragmentWeather, weatherFragment)
//                    .commit();
//        }
    }

    public static Intent getIntent(@NonNull Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                IntentHelper.openSettingsActivity(this);
                return true;
            case R.id.nav_about:
                IntentHelper.openAboutActivity(this);
                return true;
            default:
                return true;
        }
    }
}
