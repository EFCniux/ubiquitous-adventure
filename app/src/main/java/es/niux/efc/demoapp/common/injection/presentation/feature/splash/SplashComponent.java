package es.niux.efc.demoapp.common.injection.presentation.feature.splash;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import es.niux.efc.demoapp.common.injection.scope.PerActivity;
import es.niux.efc.demoapp.presentation.feature.splash.SplashActivityImpl;

@PerActivity
@Subcomponent(modules = {
        SplashModule.class
})
public interface SplashComponent extends AndroidInjector<SplashActivityImpl> {
    @Subcomponent.Builder abstract class Builder
            extends AndroidInjector.Builder<SplashActivityImpl>
    { }
}
