package es.niux.efc.core.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import es.niux.efc.core.presentation.navigator.INavigator;
import es.niux.efc.core.presentation.presenter.IPresenter;

public abstract class Activity extends AppCompatActivity {
    private @Nullable IPresenter presenter;

    @CallSuper @Override
    protected void onCreate(@Nullable Bundle toRestoreState) {
        super.onCreate(toRestoreState);
        IPresenter presenter = onCreatePresenter();
        if (presenter != null) {
            presenter.onCreate();
            if (toRestoreState != null) {
                presenter.onRestoreInstance(toRestoreState);
            }
        }
        this.presenter = presenter;
    }

    protected abstract @Nullable IPresenter onCreatePresenter();

    @CallSuper @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) presenter.onStart();
    }

    @CallSuper @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) presenter.onResume();
    }

    @CallSuper @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null) presenter.onPause();
    }

    @CallSuper @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) presenter.onStop();
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(@NonNull Bundle toSaveState) {
        super.onSaveInstanceState(toSaveState);
        if (presenter != null) presenter.onSaveInstance(toSaveState);
    }

    @CallSuper @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (presenter != null) {
            INavigator navigator = presenter.getNavigator();
            if (navigator != null && navigator.onNavigateBack()) {
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (presenter != null) {
                    INavigator navigator = presenter.getNavigator();
                    if (navigator != null && navigator.onNavigateUp()) {
                        return true;
                    } else return super.onOptionsItemSelected(item);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
