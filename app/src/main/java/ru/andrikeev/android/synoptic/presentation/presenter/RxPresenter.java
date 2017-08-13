package ru.andrikeev.android.synoptic.presentation.presenter;

import android.support.annotation.Nullable;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Base presenter that provides Rx subscription for async operations and automatically
 * dispose subscription when presenter is destroyed.
 */
public abstract class RxPresenter<View extends MvpView> extends MvpPresenter<View> {

    @Nullable
    protected List<Disposable> subscriptions = new ArrayList<>();

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (Disposable subscription:subscriptions) {
            if (subscription != null) {
                subscription.dispose();
            }
        }
    }
}
