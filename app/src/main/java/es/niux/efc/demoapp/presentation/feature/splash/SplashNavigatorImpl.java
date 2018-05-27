package es.niux.efc.demoapp.presentation.feature.splash;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import es.niux.efc.core.common.util.Check;
import es.niux.efc.core.presentation.navigator.Navigator;
import es.niux.efc.demoapp.presentation.feature.movie.list.MovieListActivityImpl;

public class SplashNavigatorImpl extends Navigator implements SplashNavigator {
    private final @NonNull SplashActivity activity;

    @Inject
    protected SplashNavigatorImpl(@NonNull SplashActivity activity) {
        super(activity.getActivity());
        this.activity = Check.nonNull(activity);
    }

    @Override
    public void onNavigateMain() {
        activityChange(MovieListActivityImpl.getCallingIntent(activity.getActivity()));
    }
}
