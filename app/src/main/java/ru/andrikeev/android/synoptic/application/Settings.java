package ru.andrikeev.android.synoptic.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.utils.units.TemperatureUnits;

/**
 * Application settings.
 */
@Singleton
public class Settings {

    private SharedPreferences preferences;

    private Context context;

    @Inject
    public Settings(@NonNull SharedPreferences preferences,
                    @NonNull Context context) {
        this.preferences = preferences;
        this.context = context;
    }

    /**
     * Theme settings.
     */
    public boolean isNightMode() {
        return preferences.getBoolean(context.getString(R.string.pref_night_mode_key),
                context.getResources().getBoolean(R.bool.pref_night_mode_default));
    }

    /**
     * Weather synchronization interval.
     */
    public int getSyncInterval() {
        // WA for ListPreferenceCompat, it use String for both descriptions and values
        return Integer.valueOf(preferences.getString(context.getString(R.string.pref_sync_weather_interval_key),
                context.getString(R.string.pref_sync_weather_interval_default)));
    }

    /**
     * Is background weather synchronization enabled.
     */
    public boolean isSyncEnabled() {
        return preferences.getBoolean(context.getString(R.string.pref_sync_weather_key),
                context.getResources().getBoolean(R.bool.pref_sync_weather_default));
    }

    /**
     * Temperature units.
     */
    public TemperatureUnits getTempUnits() {
        return TemperatureUnits.valueOf(preferences.getString(context.getString(R.string.pref_temp_units_key),
                context.getString(R.string.pref_temp_units_default)));
    }

    /**
     * Get supported locale code.
     */
    public String getLocale() {
        HashSet<String> supportedLocales = new HashSet<>();
        Collections.addAll(supportedLocales, context.getResources().getStringArray(R.array.pref_locales));
        String locale = Locale.getDefault().getLanguage();
        if (supportedLocales.contains(locale)) {
            return locale;
        } else {
            return context.getString(R.string.pref_locale_default);
        }
    }

    public long getCityId(){
        return preferences.getLong(context.getString(R.string.pref_city_id),
                Long.decode(context.getString(R.string.pref_city_id_default)));
    }

    public void setCityId(long id){
        preferences.edit()
                .putLong(context.getString(R.string.pref_city_id),id)
                .apply();
    }

    public boolean isFirstStart(){
        return preferences.getBoolean(context.getString(R.string.pref_first_start), true);
    }

    public void setFirstStart(boolean start){
        preferences.edit()
                .putBoolean(context.getString(R.string.pref_first_start),start)
                .apply();
    }
}
