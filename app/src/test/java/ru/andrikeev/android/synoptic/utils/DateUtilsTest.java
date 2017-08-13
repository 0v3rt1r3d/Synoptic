package ru.andrikeev.android.synoptic.utils;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.*;

/**
 * Created by overtired on 13.08.17.
 */

@RunWith(JUnitParamsRunner.class)
public class DateUtilsTest {

    @Test
    @Parameters({"1502624489453, 13 августа 11:41","1097557954000, 12 октября 05:12"})
    public void formatWeatherDate(long dateInLong, String formattedDate) throws Exception {
        String result = DateUtils.formatWeatherDate(new Date(dateInLong));
        assertEquals("Formatted weather date",result,formattedDate);
    }

    @Test
    @Parameters({"1502624489453, ВОСКРЕСЕНЬЕ 11:41","1097557954000, ВТОРНИК 05:12"})
    public void formatForecastDate(long dateInLong, String formattedDate) throws Exception {
        String result = DateUtils.formatForecastDate(new Date(dateInLong));
        assertEquals("Formatted forecast date",result,formattedDate);
    }

    @Test
    @Parameters({"1502624489453, ВОСКРЕСЕНЬЕ 13 АВГУСТА","1097557954000, ВТОРНИК 12 ОКТЯБРЯ"})
    public void formatDailyForecastDate(long dateInLong, String formattedDate) throws Exception {
        String result = DateUtils.formatDailyForecastDate(new Date(dateInLong));
        assertEquals("Formatted forecast date",result,formattedDate);
    }

}