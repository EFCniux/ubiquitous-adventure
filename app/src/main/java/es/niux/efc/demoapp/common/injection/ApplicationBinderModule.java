package es.niux.efc.demoapp.common.injection;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import es.niux.efc.demoapp.common.injection.presentation.feature.film.list.FilmListComponent;
import es.niux.efc.demoapp.common.injection.presentation.feature.splash.SplashComponent;
import es.niux.efc.demoapp.presentation.feature.film.list.FilmListActivityImpl;
import es.niux.efc.demoapp.presentation.feature.splash.SplashActivityImpl;

@Module
abstract class ApplicationBinderModule {
    @Binds @IntoMap @ActivityKey(SplashActivityImpl.class)
    abstract AndroidInjector.Factory<? extends Activity> bindSplashActivityInjectorFactory(
            SplashComponent.Builder builder
    );

    @Binds @IntoMap @ActivityKey(FilmListActivityImpl.class)
    abstract AndroidInjector.Factory<? extends Activity> bindFilmListActivityInjectorFactory(
            FilmListComponent.Builder builder
    );
}
