package ru.andrikeev.android.synoptic.model.persistence;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import io.requery.Entity;
import io.requery.Key;
import io.requery.Persistable;
import io.requery.Table;

/**
 * Created by overtired on 08.08.17.
 */

@Entity
@Table(name = "cities")
@AutoValue
public abstract class City implements Persistable{
    public static final float NULL_MESSAGE = 0.0f;

    @Key
    public abstract long cityId();
    public abstract String cityName();
    public abstract float lastMessage();

    public static Builder builder(){
        return new AutoValue_City.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder{
        public abstract Builder setCityId(long id);
        public abstract Builder setCityName(@NonNull String cityName);
        public abstract Builder setLastMessage(float message);
        public abstract City build();
    }
}
