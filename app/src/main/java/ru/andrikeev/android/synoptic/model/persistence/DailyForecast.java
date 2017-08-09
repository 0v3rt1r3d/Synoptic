package ru.andrikeev.android.synoptic.model.persistence;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Persistable;
import io.requery.Table;

/**
 * Created by overtired on 01.08.17.
 */

@Entity
@Table(name = "daily_forecast")
@AutoValue
public abstract class DailyForecast implements Persistable {
    public static final int NO_ID = -1;

    @Key
    @Generated
    public abstract int id();
    public abstract int weatherIconId();
    public abstract float message();
    public abstract String cityName();
    public abstract long cityId();
    public abstract long timestamp();
    public abstract String description();
    public abstract float clouds();
    public abstract float windSpeed();
    public abstract float windDegree();
    public abstract float pressure();
    public abstract float humidity();
    public abstract float tempMorning();
    public abstract float tempEvening();
    public abstract float tempNight();
    public abstract float tempDay();

    public static Builder builder() {
        return new AutoValue_DailyForecast.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setId(int id);
        public abstract Builder setWeatherIconId(int iconId);
        public abstract Builder setMessage(float message);
        public abstract Builder setCityName(String cityName);
        public abstract Builder setCityId(long cityId);
        public abstract Builder setTimestamp(long timestamp);
        public abstract Builder setDescription(String description);
        public abstract Builder setClouds(float clouds);
        public abstract Builder setWindSpeed(float windSpeed);
        public abstract Builder setWindDegree(float windDegree);
        public abstract Builder setPressure(float pressure);
        public abstract Builder setHumidity(float humidity);
        public abstract Builder setTempMorning(float tempMorning);
        public abstract Builder setTempEvening(float tempEvening);
        public abstract Builder setTempNight(float tempNight);
        public abstract Builder setTempDay(float tempDay);
        public abstract DailyForecast build();
    }
}
