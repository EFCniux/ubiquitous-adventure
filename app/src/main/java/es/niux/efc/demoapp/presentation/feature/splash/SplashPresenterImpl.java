package es.niux.efc.demoapp.presentation.feature.splash;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import es.niux.efc.core.common.util.Check;
import es.niux.efc.core.presentation.navigator.INavigator;

public class SplashPresenterImpl implements SplashPresenter {
    private final @NonNull SplashNavigator navigator;

    @Inject
    public SplashPresenterImpl(
            @NonNull SplashNavigator navigator
    ) {
        this.navigator = Check.nonNull(navigator);
    }

    @Override
    public void onResume() {
        navigator.onNavigateMain();
    }

    @Override
    public @Nullable INavigator getNavigator() {
        return navigator;
    }
}
