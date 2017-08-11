package ru.andrikeev.android.synoptic.ui.fragment.city;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.model.data.SuggestionModel;
import ru.andrikeev.android.synoptic.model.persistence.City;
import ru.andrikeev.android.synoptic.presentation.presenter.city.CityPresenter;
import ru.andrikeev.android.synoptic.presentation.view.CityView;
import ru.andrikeev.android.synoptic.ui.fragment.BaseFragment;
import ru.andrikeev.android.synoptic.utils.IntentHelper;

/**
 * Created by overtired on 25.07.17.
 */

public class CityFragment extends BaseFragment<CityView, CityPresenter>
        implements CityView, OnSuggestionClickListener, OnCityClickListener {
    private ImageView searchImage;
    private EditText editText;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private CityAdapter adapter;

    @Inject
    @InjectPresenter
    CityPresenter presenter;

    @ProvidePresenter
    protected CityPresenter provideCityPresenter() {
        return presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.edit_city);
        searchImage = view.findViewById(R.id.image_search);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler_city);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CityAdapter(this,this);
        recyclerView.setAdapter(adapter);

        presenter.onTextChanged(RxTextView
                .textChanges(editText)
                .debounce(400, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .map(CharSequence::toString));
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_city;
    }

    @Override
    protected CityPresenter providePresenter() {
        return presenter;
    }

    @Override
    public void setSuggestions(@NonNull List<SuggestionModel> cities) {
        adapter.clear();
        adapter.notifyDataSetChanged();
        adapter.addSuggestions(cities);
        adapter.notifyItemRangeChanged(0, cities.size());
    }

    @Override
    public void setCities(@NonNull List<City> cities) {
        adapter.clear();
        adapter.notifyDataSetChanged();
        adapter.addCities(cities);
        adapter.notifyItemRangeChanged(0,cities.size());
    }

    @Override
    public void showLoading() {
        editText.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        searchImage.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        searchImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressAndExit() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
        IntentHelper.openMainActivity(getActivity());
    }

    @Override
    public void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show(); // TODO: show error
    }

    @Override
    public void showCityRemoved(@NonNull City city) {
        //// TODO: 10.08.17 strings
        Toast.makeText(getActivity(),"City removed!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuggestionClick(@NonNull SuggestionModel model) {
        presenter.loadCity(model.getPlaceID());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }

    @Override
    public void onCityClick(@NonNull City city) {
        presenter.onCitySelected(city);
    }

    @Override
    public void onCityRemoveClick(@NonNull City city) {
        presenter.onCityRemoved(city);
        //todo update list
    }
}
