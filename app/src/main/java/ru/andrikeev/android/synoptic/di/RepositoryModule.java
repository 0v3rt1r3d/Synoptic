package ru.andrikeev.android.synoptic.di;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.reactivex.ReactiveEntityStore;
import io.requery.reactivex.ReactiveSupport;
import io.requery.sql.ConfigurationBuilder;
import io.requery.sql.EntityDataStore;
import ru.andrikeev.android.synoptic.model.persistence.Models;

/**
 * Module for local data dependencies.
 */
@Module
final class RepositoryModule {

    private static final String DB_NAME = "weather";

    /**
     * Provides {@link ReactiveEntityStore} for accessing DB in a reactive way.
     *
     * @return Rx data store
     */
    @Provides
    @Singleton
    @NonNull
    ReactiveEntityStore<Persistable> provideDataStore(@NonNull Context context) {
        final DatabaseSource source = new DatabaseSource(context, Models.DEFAULT, DB_NAME, 1) {
            @Override
            protected void onConfigure(ConfigurationBuilder builder) {
                super.onConfigure(builder);
                builder.setQuoteColumnNames(true);
            }
        };
        return ReactiveSupport.toReactiveStore(new EntityDataStore<Persistable>(source.getConfiguration()));
    }
}
