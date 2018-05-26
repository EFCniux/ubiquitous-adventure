package es.niux.efc.demoapp.common.injection.presentation.feature.splash;

import dagger.Binds;
import dagger.Module;
import es.niux.efc.demoapp.common.injection.scope.PerActivity;
import es.niux.efc.demoapp.presentation.feature.splash.SplashActivity;
import es.niux.efc.demoapp.presentation.feature.splash.SplashActivityImpl;
import es.niux.efc.demoapp.presentation.feature.splash.SplashNavigator;
import es.niux.efc.demoapp.presentation.feature.splash.SplashNavigatorImpl;
import es.niux.efc.demoapp.presentation.feature.splash.SplashPresenter;
import es.niux.efc.demoapp.presentation.feature.splash.SplashPresenterImpl;

@Module
abstract class SplashModule {
    @Binds @PerActivity abstract SplashActivity provideSplashActivity(
            SplashActivityImpl splashActivity
    );

    @Binds @PerActivity abstract SplashNavigator provideSplashNavigator(
            SplashNavigatorImpl splashNavigator
    );

    @Binds @PerActivity abstract SplashPresenter provideSplashPresenter(
            SplashPresenterImpl splashPresenter
    );
}
