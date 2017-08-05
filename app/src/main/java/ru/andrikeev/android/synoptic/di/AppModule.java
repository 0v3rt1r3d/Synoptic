package ru.andrikeev.android.synoptic.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.andrikeev.android.synoptic.model.GsonAdapterFactory;

/**
 * Application module.
 */
@Module
final class AppModule {

    @NonNull
    private Application application;

    AppModule(@NonNull final Application application) {
        this.application = application;
    }

    /**
     * Provides application context.
     *
     * @return application context
     */
    @Provides
    @Singleton
    @NonNull
    Context provideContext() {
        return application.getApplicationContext();
    }

    /**
     * Provides shared preferences storage for application settings.
     *
     * @return shared preferences storage
     */
    @Provides
    @Singleton
    @NonNull
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
