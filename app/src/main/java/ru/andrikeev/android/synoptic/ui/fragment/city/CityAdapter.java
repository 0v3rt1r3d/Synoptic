package ru.andrikeev.android.synoptic.ui.fragment.city;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.model.data.SuggestionModel;
import ru.andrikeev.android.synoptic.presentation.presenter.city.CityPresenter;

/**
 * Created by overtired on 28.07.17.
 */

public class CityAdapter extends Adapter<CityAdapter.CityHolder>{

    private List<SuggestionModel> cities;
    private OnCityClickListener listener;

    public CityAdapter(@NonNull OnCityClickListener listener){
        this.cities = new ArrayList<>();
        this.listener = listener;
    }

    public void add(@NonNull List<SuggestionModel> cities){
        this.cities.addAll(cities);
    }

    public void clear(){
        this.cities.clear();
    }

    @Override
    public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_city,parent,false);
        return new CityHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(CityHolder holder, int position) {
        holder.setCity(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class CityHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private SuggestionModel city;
        private OnCityClickListener listener;
        private TextView textView;

        public CityHolder(View view, OnCityClickListener listener){
            super(view);
            this.textView = view.findViewById(R.id.city_text);
            this.listener = listener;

            view.setOnClickListener(this);
        }

        public void setCity(SuggestionModel city){
            this.city = city;
            updateHolder();
        }

        private void updateHolder(){
            textView.setText(city.getName());
        }

        @Override
        public void onClick(View view) {
            listener.onCityClick(city);
        }
    }
}
