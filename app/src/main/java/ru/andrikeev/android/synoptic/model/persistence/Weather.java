package ru.andrikeev.android.synoptic.model.persistence;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import io.requery.Entity;
import io.requery.Key;
import io.requery.Persistable;
import io.requery.Table;

/**
 * Сущность - текущее состояние погоды.
 */
@Entity
@Table(name = "weather")
@AutoValue
public abstract class Weather implements Persistable{

    /**
     * Идентификатор погоды - совпадает с идентификатором города.
     */
    @Key
    public abstract long cityId();

    /**
     * Дата и время.
     */
    public abstract long timestamp();

    /**
     * Идентифиактор состояния погоды.
     */
    public abstract int weatherId();

    /**
     * Полное описание.
     */
    public abstract String description();

    /**
     * Температура.
     */
    public abstract float temperature();

    /**
     * Давление.
     */
    public abstract float pressure();

    /**
     * Влажность.
     */
    public abstract float humidity();

    /**
     * Скорость ветра.
     */
    public abstract float windSpeed();

    /**
     * Направление (азимут) ветра.
     */
    public abstract float windDegree();

    /**
     * Облачность в процентах.
     */
    public abstract float clouds();

    public static Builder builder() {
        return new AutoValue_Weather.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setCityId(long cityId);
        public abstract Builder setTimestamp(long timestamp);
        public abstract Builder setWeatherId(int weatherId);
        public abstract Builder setDescription(String description);
        public abstract Builder setTemperature(float temperature);
        public abstract Builder setPressure(float pressure);
        public abstract Builder setHumidity(float humidity);
        public abstract Builder setWindSpeed(float windSpeed);
        public abstract Builder setWindDegree(float windDegree);
        public abstract Builder setClouds(float clouds);

        public abstract Weather build();
    }
}
