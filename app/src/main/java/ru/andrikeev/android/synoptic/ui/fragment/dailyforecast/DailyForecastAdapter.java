package ru.andrikeev.android.synoptic.ui.fragment.dailyforecast;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private List<DailyForecastItem> forecasts;

    public DailyForecastAdapter() {
        this.forecasts = new ArrayList<>();
    }

    public void clear() {
        this.forecasts.clear();
    }

    public void add(@NonNull List<DailyForecastItem> forecasts) {
        this.forecasts.addAll(forecasts);
    }

    @Override
    public DailyForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_daily_forecast, parent, false);
        return new DailyForecastHolder(v);
    }

    @Override
    public void onBindViewHolder(DailyForecastHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    public class DailyForecastHolder extends RecyclerView.ViewHolder {

        private ForecastModel forecast;

        public void setForecast(@NonNull ForecastModel forecast) {
            this.forecast = forecast;
            updateHolder();
        }

        private void updateHolder() {
            //todo:
        }

        public DailyForecastHolder(View itemView) {
            super(itemView);
        }
    }
}
