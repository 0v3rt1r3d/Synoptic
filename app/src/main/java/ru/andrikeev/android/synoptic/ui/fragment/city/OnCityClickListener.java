package ru.andrikeev.android.synoptic.ui.fragment.city;

import android.support.annotation.NonNull;

import ru.andrikeev.android.synoptic.model.persistence.City;

/**
 * Created by overtired on 10.08.17.
 */

public interface OnCityClickListener {
    void onCityClick(@NonNull City city);
    void onCityRemoveClick(@NonNull City city);
}
