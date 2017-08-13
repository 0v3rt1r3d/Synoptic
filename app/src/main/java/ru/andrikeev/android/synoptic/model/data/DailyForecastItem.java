package ru.andrikeev.android.synoptic.model.data;

import com.google.auto.value.AutoValue;

import java.util.Date;

/**
 * Created by overtired on 01.08.17.
 */
@AutoValue
public abstract class DailyForecastItem {
    public abstract int windDirectionIconId();
    public abstract int weatherIconId();

    public abstract String date();
    public abstract String tempDay();
    public abstract String tempNight();
    public abstract String tempEvening();
    public abstract String tempMorning();
    public abstract String temperatureUnits();
    public abstract String description();
    public abstract String pressure();
    public abstract String humidity();
    public abstract String windSpeed();
    public abstract String clouds();

    public static DailyForecastItem create(
            int windDirectionIconId,
            int weatherIconId,
            String date,
            String tempDay,
            String tempNight,
            String tempEvening,
            String tempMorning,
            String temperatureUnits,
            String description,
            String pressure,
            String humidity,
            String windSpeed,
            String clouds){
        return new AutoValue_DailyForecastItem(
                windDirectionIconId,
                weatherIconId,
                date,
                tempDay,
                tempNight,
                tempEvening,
                tempMorning,
                temperatureUnits,
                description,
                pressure,
                humidity,
                windSpeed,
                clouds
        );
    }
}
