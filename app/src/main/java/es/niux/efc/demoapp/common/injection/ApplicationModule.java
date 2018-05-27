package es.niux.efc.demoapp.common.injection;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.niux.efc.core.common.util.Check;
import es.niux.efc.demoapp.TheApplication;
import es.niux.efc.demoapp.common.ApplicationSchedulers;
import es.niux.efc.demoapp.common.ApplicationSchedulersImpl;
import es.niux.efc.demoapp.common.injection.data.DataRepositoryModule;
import es.niux.efc.demoapp.common.injection.data.DataSourceModule;
import es.niux.efc.demoapp.common.injection.domain.DomainInteractorDataSource;
import es.niux.efc.demoapp.common.injection.presentation.feature.movie.list.MovieListComponent;
import es.niux.efc.demoapp.common.injection.presentation.feature.splash.SplashComponent;

@Module(includes = {
        DataSourceModule.class,
        DataRepositoryModule.class,
        DomainInteractorDataSource.class,
}, subcomponents = {
        SplashComponent.class,
        MovieListComponent.class,
})
class ApplicationModule {
    @Provides @Singleton Context provideApplicationContext(
            TheApplication application
    ) {
        return Check.nonNull(application).getApplicationContext();
    }

    @Provides @Singleton ApplicationSchedulers provideApplicationSchedulersImpl(
            ApplicationSchedulersImpl applicationSchedulers
    ) {
        return Check.nonNull(applicationSchedulers);
    }
}
