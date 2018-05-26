package es.niux.efc.demoapp.presentation.feature.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import es.niux.efc.core.presentation.presenter.IPresenter;
import es.niux.efc.demoapp.presentation.view.activity.BaseActivity;

public class SplashActivityImpl extends BaseActivity implements SplashActivity {
    @Inject SplashPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle toRestoreState) {
        AndroidInjection.inject(this);
        super.onCreate(toRestoreState);
    }

    @Override
    protected @Nullable IPresenter onCreatePresenter() {
        return presenter;
    }
}
