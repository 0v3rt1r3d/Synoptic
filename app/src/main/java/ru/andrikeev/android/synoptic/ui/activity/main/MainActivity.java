package ru.andrikeev.android.synoptic.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.ui.activity.BaseActivity;
import ru.andrikeev.android.synoptic.ui.fragment.dailyforecast.DailyForecastFragment;
import ru.andrikeev.android.synoptic.ui.fragment.forecast.ForecastFragment;
import ru.andrikeev.android.synoptic.ui.fragment.weather.WeatherFragment;
import ru.andrikeev.android.synoptic.utils.IntentHelper;

/**
 * Главный экран приложения с боковой шторкой.
 */
public class MainActivity extends BaseActivity
        implements HasSupportFragmentInjector, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    protected DispatchingAndroidInjector<Fragment> injector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return injector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navigationView.getMenu().getItem(0).setChecked(true);
            WeatherFragment weatherFragment = WeatherFragment.create();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, weatherFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.nav_weather:
                fragment = getSupportFragmentManager()
                        .findFragmentByTag(WeatherFragment.TAG);
                if(fragment == null){
                    fragment = WeatherFragment.create();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,fragment,WeatherFragment.TAG)
                        .commit();
                break;

            case R.id.nav_forecast:
                fragment = getSupportFragmentManager()
                        .findFragmentByTag(ForecastFragment.TAG);
                if(fragment == null){
                    fragment = ForecastFragment.create();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,fragment,ForecastFragment.TAG)
                        .commit();
                break;

            case R.id.nav_daily_forecast:
                fragment = getSupportFragmentManager()
                        .findFragmentByTag(DailyForecastFragment.TAG);
                if(fragment == null){
                    fragment = DailyForecastFragment.create();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,fragment,DailyForecastFragment.TAG)
                        .commit();
                break;

            case R.id.nav_settings:
                IntentHelper.openSettingsActivity(this);
                return false;

            case R.id.nav_about:
                IntentHelper.openAboutActivity(this);
                return false;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Intent getIntent(@NonNull Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }
}
