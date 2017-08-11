package ru.andrikeev.android.synoptic.utils;


import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper class for converting dates.
 */
public class DateUtils {

    public static String formatWeatherDate(@NonNull Date date) {
        DateFormat shortenedDateFormat = new SimpleDateFormat("d EEEE MMMM HH:mm");
        return shortenedDateFormat.format(date);
    }

    public static String formatForecastDate(@NonNull Date date){
        DateFormat dateFormat = new SimpleDateFormat("EEEE HH:mm");
        return dateFormat.format(date);
    }

    public static String formatDailyForecastDate(@NonNull Date date){
        DateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM");
        return dateFormat.format(date).toUpperCase();
    }
}
