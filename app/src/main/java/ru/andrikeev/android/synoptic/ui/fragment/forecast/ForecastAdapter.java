package ru.andrikeev.android.synoptic.ui.fragment.forecast;

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

/**
 * Created by overtired on 07.08.17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastHolder>{

    private ForecastModel forecast;

    public void setForecast(@NonNull ForecastModel forecast){
        this.forecast = forecast;
        //todo: set cityName
    }

    @Override
    public ForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_forecast,parent,false);
        return new ForecastHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastHolder holder, int position) {
        holder.setItem(forecast.items().get(position));
    }

    @Override
    public int getItemCount() {
        if(forecast!=null && forecast.items()!=null){
            return forecast.items().size();
        }
        return 0;
    }

    public class ForecastHolder extends RecyclerView.ViewHolder{
        private ForecastItem item;

        private TextView temperature;
        private TextView temperatureUnits;
        private TextView windSpeed;
        private TextView humidity;
        private TextView pressure;
        private TextView date;
        private TextView clouds;
        private TextView description;

        private ImageView weatherIcon;
        private ImageView windDirectionIcon;

        public void setItem(@NonNull ForecastItem item){
            this.item = item;
            updateHolder();
        }

        private void updateHolder(){
            temperature.setText(item.temperature());
            temperatureUnits.setText(item.temperatureUnits());
            windSpeed.setText(item.windSpeed());
            humidity.setText(item.humidity());
            pressure.setText(item.pressure());
            date.setText(item.date());//todo: other type
            clouds.setText(item.clouds());
            description.setText(item.description());
            weatherIcon.setImageResource(item.weatherIconId());
            windDirectionIcon.setImageResource(item.windDirectionIconId());
        }

        public ForecastHolder(View itemView) {
            super(itemView);
            temperature = itemView.findViewById(R.id.temperature);
            temperatureUnits = itemView.findViewById(R.id.temperatureUnits);
            windSpeed = itemView.findViewById(R.id.windSpeed);
            humidity = itemView.findViewById(R.id.humidity);
            pressure = itemView.findViewById(R.id.pressure);
            date = itemView.findViewById(R.id.date);
            clouds = itemView.findViewById(R.id.clouds);
            description = itemView.findViewById(R.id.description);

            weatherIcon = itemView.findViewById(R.id.weatherIcon);
            windDirectionIcon = itemView.findViewById(R.id.windDirectionIcon);
        }
    }
}
