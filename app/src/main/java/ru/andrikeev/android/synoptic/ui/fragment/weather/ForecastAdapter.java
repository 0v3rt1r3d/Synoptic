package ru.andrikeev.android.synoptic.ui.fragment.weather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.model.data.ForecastItem;
import ru.andrikeev.android.synoptic.model.data.ForecastModel;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;

/**
 * Created by overtired on 07.08.17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ForecastModel forecast;
    private WeatherModel weather;

    public void setForecast(@NonNull ForecastModel forecast) {
        this.forecast = forecast;
    }

    public void setWeather(@NonNull WeatherModel weather) {
        this.weather = weather;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return WeatherHolder.VIEW_TYPE;
        } else {
            return ForecastHolder.VIEW_TYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case WeatherHolder.VIEW_TYPE:
                view = inflater.inflate(R.layout.item_weather, parent, false);
                return new WeatherHolder(view);
            default:
                view = inflater.inflate(R.layout.item_forecast, parent, false);
                return new ForecastHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ((WeatherHolder) holder).setWeather(weather);
        } else {
            ((ForecastHolder) holder).setItem(forecast.items().get(position-1));
        }
    }

    @Override
    public int getItemCount() {
        if (forecast != null && forecast.items() != null && weather!=null) {
            return forecast.items().size()+1;
        }
        return 0;
    }

    public class WeatherHolder extends RecyclerView.ViewHolder {

        public static final int VIEW_TYPE = 0;

        private WeatherModel model;

        private TextView lastUpdate;
        private ImageView weatherIcon;
        private TextView temperature;
        private TextView temperatureUnits;
        private TextView description;
        private TextView pressure;
        private TextView humidity;
        private TextView wind;
        private ImageView windDirection;
        private TextView clouds;

        public WeatherHolder(View view) {
            super(view);
            lastUpdate = view.findViewById(R.id.lastUpdate);
            weatherIcon = view.findViewById(R.id.weatherIcon);
            temperature = view.findViewById(R.id.temperature);
            temperatureUnits = view.findViewById(R.id.temperatureUnits);
            description = view.findViewById(R.id.description);
            pressure = view.findViewById(R.id.pressure);
            humidity = view.findViewById(R.id.humidity);
            wind = view.findViewById(R.id.wind);
            windDirection = view.findViewById(R.id.windDirection);
            clouds = view.findViewById(R.id.clouds);
        }

        public void setWeather(@NonNull WeatherModel model) {
            this.model = model;
            update();
        }

        private void update() {
            if (forecast != null && weather != null) {

                lastUpdate.setText(model.date());
                weatherIcon.setImageResource(model.weatherIconId());
                temperature.setText(model.temperature());
                temperatureUnits.setText(model.temperatureUnits());
                description.setText(model.description());
                pressure.setText(model.pressure());
                humidity.setText(model.humidity());
                wind.setText(model.windSpeed());
                windDirection.setImageResource(model.windDirectionIconId());
                clouds.setText(model.clouds());
            }
        }
    }

    public class ForecastHolder extends RecyclerView.ViewHolder {
        public static final int VIEW_TYPE = 1;

        private ForecastItem item;

        private TextView temperature;
        private TextView temperatureUnits;
        private TextView date;
        private TextView description;

        private ImageView weatherIcon;

        public void setItem(@NonNull ForecastItem item) {
            this.item = item;
            updateHolder();
        }

        private void updateHolder() {
            temperature.setText(item.temperature());
            temperatureUnits.setText(item.temperatureUnits());
            date.setText(item.date());
            description.setText(item.description());
            weatherIcon.setImageResource(item.weatherIconId());
        }

        public ForecastHolder(View itemView) {
            super(itemView);
            temperature = itemView.findViewById(R.id.temperature);
            temperatureUnits = itemView.findViewById(R.id.temperatureUnits);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.description);

            weatherIcon = itemView.findViewById(R.id.weatherIcon);
        }
    }
}
