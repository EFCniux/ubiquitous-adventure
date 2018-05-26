package es.niux.efc.core.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.niux.efc.core.presentation.presenter.IPresenter;

public abstract class Fragment extends android.support.v4.app.Fragment {
    private @Nullable IPresenter presenter;

    @CallSuper @Override
    public void onCreate(@Nullable Bundle toRestoreState) {
        super.onCreate(toRestoreState);
        IPresenter presenter = onCreatePresenter();
        if (presenter !=  null) {
            presenter.onCreate();
            if (toRestoreState != null) {
                presenter.onRestoreInstance(toRestoreState);
            }
        }
        this.presenter = presenter;
    }

    protected abstract @Nullable IPresenter onCreatePresenter();

    @Override
    public abstract @NonNull View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle toRestoreState
    );

    @CallSuper @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) presenter.onStart();
    }

    @CallSuper @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) presenter.onResume();
    }

    @CallSuper @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) presenter.onPause();
    }

    @CallSuper @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) presenter.onStop();
    }

    @CallSuper @Override
    public void onSaveInstanceState(@NonNull Bundle toSaveState) {
        super.onSaveInstanceState(toSaveState);
        if (presenter != null) presenter.onSaveInstance(toSaveState);
    }

    @CallSuper @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.onDestroy();
    }


    @Override
    public @NonNull Context getContext() {
        return requireContext();
    }

    @Override
    public @NonNull View getView() {
        View view = super.getView();
        if (view == null) {
            throw new IllegalStateException("Fragment view not yet created!");
        } return view;
    }
}
