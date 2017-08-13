package ru.andrikeev.android.synoptic.ui.fragment.city;

import android.support.annotation.NonNull;

import ru.andrikeev.android.synoptic.model.data.SuggestionModel;

/**
 * Created by overtired on 28.07.17.
 */

public interface OnSuggestionClickListener {
    void onSuggestionClick(@NonNull SuggestionModel model);
}
