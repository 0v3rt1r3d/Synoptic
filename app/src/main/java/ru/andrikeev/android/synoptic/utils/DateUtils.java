package ru.andrikeev.android.synoptic.utils;


import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.text.DateFormat.SHORT;

/**
 * Helper class for converting dates.
 */
public class DateUtils {

    public static String formatWeatherDate(@NonNull Date date) {
        DateFormat shortenedDateFormat = SimpleDateFormat.getDateTimeInstance(SHORT, SHORT);
        return shortenedDateFormat.format(date);
    }

    public static String formatForecastDate(@NonNull Date date){
        DateFormat dateFormat = new SimpleDateFormat("");
        return dateFormat.format(date);
    }

    public static String formatDailyForecastDate(@NonNull Date date){
        DateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM");
        return dateFormat.format(date).toUpperCase();
    }
}
