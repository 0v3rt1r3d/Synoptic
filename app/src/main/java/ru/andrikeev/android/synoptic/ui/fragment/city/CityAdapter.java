package ru.andrikeev.android.synoptic.ui.fragment.city;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.model.data.SuggestionModel;
import ru.andrikeev.android.synoptic.model.persistence.City;

/**
 * Created by overtired on 28.07.17.
 */

public class CityAdapter extends Adapter<RecyclerView.ViewHolder> {

    private List<SuggestionModel> suggestions;
    private List<City> cities;
    private OnSuggestionClickListener onSuggestionClickListener;
    private OnCityClickListener onCityClickListener;

    public CityAdapter(@NonNull OnSuggestionClickListener onSuggestionClickListener,
                       @NonNull OnCityClickListener onCityClickListener) {
        this.cities = new ArrayList<>();
        this.suggestions = new ArrayList<>();
        this.onSuggestionClickListener = onSuggestionClickListener;
        this.onCityClickListener = onCityClickListener;
    }

    public void addCities(@NonNull List<City> cities) {
        this.cities.addAll(cities);
    }

    public void addSuggestions(@NonNull List<SuggestionModel> suggestions) {
        this.suggestions.addAll(suggestions);
    }

    public void clear() {
        this.cities.clear();
        this.suggestions.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == CityHolder.VIEW_TYPE) {
            View v = inflater.inflate(R.layout.item_city, parent, false);
            return new CityHolder(v,onCityClickListener);
        } else {
            View v = inflater.inflate(R.layout.item_suggestion, parent, false);
            return new SuggestionHolder(v, onSuggestionClickListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < cities.size()) {
            ((CityHolder) holder).setCity(cities.get(position));
        } else {
            ((SuggestionHolder) holder).setCity(suggestions.get(cities.size() + position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < cities.size()) {
            return CityHolder.VIEW_TYPE;
        } else {
            return SuggestionHolder.VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return cities.size() + suggestions.size();
    }

    public class SuggestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public static final int VIEW_TYPE = 0;

        private SuggestionModel city;
        private OnSuggestionClickListener listener;
        private TextView textView;

        public SuggestionHolder(View view, OnSuggestionClickListener listener) {
            super(view);
            this.textView = view.findViewById(R.id.city_text);
            this.listener = listener;

            view.setOnClickListener(this);
        }

        public void setCity(@NonNull SuggestionModel city) {
            this.city = city;
            textView.setText(city.getName());
        }

        @Override
        public void onClick(View view) {
            listener.onSuggestionClick(city);
        }
    }

    public class CityHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public static final int VIEW_TYPE = 1;

        private City city;
        private TextView textView;
        private ImageView imageRemove;
        private OnCityClickListener listener;

        public CityHolder(View itemView, OnCityClickListener listener) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.city_text);
            this.imageRemove = itemView.findViewById(R.id.removeCityImage);
            this.listener = listener;
        }

        public void setCity(@NonNull City city) {
            this.city = city;
            textView.setText(city.cityName());
            imageRemove.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.listener.onCityClick(city);
        }
    }
}
