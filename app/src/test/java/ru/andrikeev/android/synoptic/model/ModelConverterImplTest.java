package ru.andrikeev.android.synoptic.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileInputStream;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.model.network.openweather.response.weather.WeatherResponse;
import ru.andrikeev.android.synoptic.model.persistence.Weather;
import ru.andrikeev.android.synoptic.utils.units.TemperatureUnits;

import static org.junit.Assert.assertEquals;

/**
 * Created by overtired on 27.07.17.
 */

public class ModelConverterImplTest {
    private static double delta = 0.0000001;

    private Context context;
    private ModelsConverter converter;

    private String jsonExample;

    private Weather expWeather = new Weather(
            2172797,
            "Cairns",
            1501164000000l,
            802,
            "scattered clouds",
            294.15f,
            1019,
            77,
            4.6f,
            180,
            40);

    private WeatherModel expWeatherModel = new WeatherModel(
            "Cairns",
            "27.07.17 14:00",
            2130837607,
            "scattered clouds",
            "21",
            "˚C",
            "1019 Pa",
            "77%",
            "5",
            2130837607,
            "40%"
    );


    @Before
    public void prepareConverter() {
        Settings settings = Mockito.mock(Settings.class);
        context = Mockito.mock(Context.class);

        Mockito.when(settings.getLocale()).thenReturn("ru");
        Mockito.when(settings.getTempUnits()).thenReturn(TemperatureUnits.CELSIUS);

        converter = new ModelsConverterImpl(settings, context);

        jsonExample = getJson("WeatherExample.json");
    }

    @Test
    public void toViewModel(){

        Mockito.when(context.getString(R.string.pref_temp_units_celsius_sign))
                .thenReturn("˚C");
        Mockito.when(context.getString(R.string.weather_pressure_pascals,1019))
                .thenReturn("1019 Pa");
        Mockito.when(context.getString(R.string.weather_humidity,77))
                .thenReturn("77%");
        Mockito.when(context.getString(R.string.weather_wind_mps, 5))
                .thenReturn("5");
        Mockito.when(context.getString(R.string.weather_clouds,40))
                .thenReturn("40%");

        WeatherModel model = converter.toViewModel(expWeather);

        assertEquals(expWeatherModel.getCityName(),model.getCityName());
        assertEquals(expWeatherModel.getDate(),model.getDate());
        assertEquals(expWeatherModel.getWeatherIconId(),model.getWeatherIconId());
        assertEquals(expWeatherModel.getDescription(),model.getDescription());
        assertEquals(expWeatherModel.getTemperature(),model.getTemperature());
        assertEquals(expWeatherModel.getTemperatureUnits(),model.getTemperatureUnits());
        assertEquals(expWeatherModel.getPressure(),model.getPressure());
        assertEquals(expWeatherModel.getHumidity(),model.getHumidity());
        assertEquals("WindSpeed",expWeatherModel.getWindSpeed(),model.getWindSpeed());
        assertEquals(expWeatherModel.getWindDirectionIconId(),model.getWeatherIconId());
        assertEquals(expWeatherModel.getClouds(),model.getClouds());
    }

    @Test
    public void toCacheModel(){
        WeatherResponse weatherResponse = new Gson()
                .fromJson(jsonExample,WeatherResponse.class);

        Weather weather = converter.toCacheModel(weatherResponse);

        assertEquals(expWeather.getCityId(),weather.getCityId());
        assertEquals(expWeather.getCityName(),weather.getCityName());
        assertEquals(expWeather.getTimestamp(),weather.getTimestamp());
        assertEquals(expWeather.getWeatherId(),weather.getWeatherId());
        assertEquals(expWeather.getDescription(),weather.getDescription());
        assertEquals(expWeather.getTemperature(),weather.getTemperature(),delta);
        assertEquals(expWeather.getPressure(),weather.getPressure(),delta);
        assertEquals(expWeather.getHumidity(),weather.getHumidity(),delta);
        assertEquals(expWeather.getWindSpeed(),weather.getWindSpeed(),delta);
        assertEquals(expWeather.getWindDegree(),weather.getWindDegree(),delta);
        assertEquals(expWeather.getClouds(),weather.getClouds(),delta);
    }

    private String getJson(@NonNull String path){
        String str;

        try {
            File file = new File("./app/src/test/res/"+path);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

             str = new String(data, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return str;
    }
}
