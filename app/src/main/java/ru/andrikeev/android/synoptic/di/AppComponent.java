package ru.andrikeev.android.synoptic.di;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import ru.andrikeev.android.synoptic.application.App;
import ru.andrikeev.android.synoptic.model.GsonAdapterFactory;

/**
 * Application component for dependencies injection.
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        BindsModule.class,
        AppModule.class,
        NetworkModule.class,
        RepositoryModule.class,
        JobModule.class,
        ActivityModule.class
})

interface AppComponent extends AndroidInjector<App> {

    @Override
    void inject(App application);
}
