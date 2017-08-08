package ru.andrikeev.android.synoptic.model.persistence;

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
@Table(name = "forecast")
@AutoValue
public abstract class Forecast implements Persistable{

    @Key @Generated
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

    public abstract float temperature();

    public abstract float pressure();

    public abstract float humidity();

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setId(int id);
        public abstract Builder setCityId(long cityId);
        public abstract Builder setCityName(String cityName);
        public abstract Builder setHumidity(float humidity);
        public abstract Builder setPressure(float pressure);
        public abstract Builder setTemperature(float temperature);
        public abstract Builder setWindDegree(float windDegree);
        public abstract Builder setWindSpeed(float windSpeed);
        public abstract Builder setClouds(float clouds);
        public abstract Builder setDescription(String description);
        public abstract Builder setTimestamp(long timestamp);
        public abstract Builder setMessage(float message);
        public abstract Builder setWeatherIconId(int weatherIconId);

        public abstract Forecast build();
    }

    public static Builder builder() {
        return new AutoValue_Forecast.Builder();
    }
}
