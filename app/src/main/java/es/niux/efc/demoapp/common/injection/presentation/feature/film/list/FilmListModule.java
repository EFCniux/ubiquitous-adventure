package es.niux.efc.demoapp.common.injection.presentation.feature.film.list;

import dagger.Binds;
import dagger.Module;
import es.niux.efc.demoapp.common.injection.scope.PerActivity;
import es.niux.efc.demoapp.presentation.feature.film.list.FilmListActivity;
import es.niux.efc.demoapp.presentation.feature.film.list.FilmListActivityImpl;

@Module
abstract class FilmListModule {
    @Binds @PerActivity abstract FilmListActivity provideSplashActivity(
            FilmListActivityImpl splashActivity
    );
}
