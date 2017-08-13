package ru.andrikeev.android.synoptic;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;

import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.model.network.openweather.response.weather.WeatherResponse;
import ru.andrikeev.android.synoptic.model.persistence.Weather;

/**
 * Created by overtired on 13.08.17.
 */

public class testDataRepository {

    public static Weather getWeather() {
        return null;
    }

    public static WeatherModel getWeatherModel() {
        return null;
    }

    public static WeatherResponse getWeatherResponse() {
        return new Gson().fromJson(getWeatherSuccessJson(),WeatherResponse.class);
    }

    private static String getWeatherSuccessJson() {
        String str;

        try {
            File file = new File("./app/src/test/res/WeatherSuccess.json");
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            str = new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return str;
    }
}
