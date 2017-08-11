package ru.andrikeev.android.synoptic.ui.fragment.dailyforecast;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.model.data.DailyForecastItem;
import ru.andrikeev.android.synoptic.model.data.DailyForecastModel;
import ru.andrikeev.android.synoptic.model.data.ForecastModel;

/**
 * Created by overtired on 03.08.17.
 */

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.DailyForecastHolder> {

    private DailyForecastModel forecasts;

    public void setDailyForecast(@NonNull DailyForecastModel forecasts) {
        this.forecasts = forecasts;
    }

    @Override
    public DailyForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_daily_forecast, parent, false);
        return new DailyForecastHolder(v);
    }

    @Override
    public void onBindViewHolder(DailyForecastHolder holder, int position) {
        holder.setItem(forecasts.items().get(position));
    }

    @Override
    public int getItemCount() {
        if(forecasts!=null && forecasts.items()!=null){
            return forecasts.items().size();
        }
        return 0;
    }

    public class DailyForecastHolder extends RecyclerView.ViewHolder {
        private DailyForecastItem item;

        private List<TextView> temperatureUnits;

        private TextView temperatureDay;
        private TextView temperatureNight;
        private TextView temperatureMorning;
        private TextView temperatureEvening;
        private TextView windSpeed;
        private TextView humidity;
        private TextView pressure;
        private TextView date;
        private TextView clouds;
        private TextView description;

        private ImageView weatherIcon;
        private ImageView windDirectionIcon;


        public void setItem(@NonNull DailyForecastItem item) {
            this.item = item;
            updateHolder();
        }

        private void updateHolder() {
            temperatureDay.setText(item.tempDay());
            temperatureNight.setText(item.tempNight());
            temperatureMorning.setText(item.tempMorning());
            temperatureEvening.setText(item.tempEvening());
            windSpeed.setText(item.windSpeed());
            humidity.setText(item.humidity());
            pressure.setText(item.pressure());
            date.setText(item.date());
            clouds.setText(item.clouds());
            description.setText(item.description());

            weatherIcon.setImageResource(item.weatherIconId());
            windDirectionIcon.setImageResource(item.windDirectionIconId());

            for(TextView view:temperatureUnits){
                view.setText(item.temperatureUnits());
            }
        }

        public DailyForecastHolder(View itemView) {
            super(itemView);
            temperatureDay = itemView.findViewById(R.id.temperatureDay);
            temperatureNight = itemView.findViewById(R.id.temperatureNight);
            temperatureMorning = itemView.findViewById(R.id.temperatureMorning);
            temperatureEvening = itemView.findViewById(R.id.temperatureEvening);
            windSpeed = itemView.findViewById(R.id.windSpeed);
            humidity = itemView.findViewById(R.id.humidity);
            pressure = itemView.findViewById(R.id.pressure);
            date = itemView.findViewById(R.id.date);
            clouds = itemView.findViewById(R.id.clouds);
            description = itemView.findViewById(R.id.description);
            weatherIcon = itemView.findViewById(R.id.weatherIcon);
            windDirectionIcon = itemView.findViewById(R.id.windDirectionIcon);

            temperatureUnits = new ArrayList<>();
            temperatureUnits.add(itemView.findViewById(R.id.temperatureUnitsMorning));
            temperatureUnits.add(itemView.findViewById(R.id.temperatureUnitsDay));
            temperatureUnits.add(itemView.findViewById(R.id.temperatureUnitsEvening));
            temperatureUnits.add(itemView.findViewById(R.id.temperatureUnitsNight));
        }
    }
}
