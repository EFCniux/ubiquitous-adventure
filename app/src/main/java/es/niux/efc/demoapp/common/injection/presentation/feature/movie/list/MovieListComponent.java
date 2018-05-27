package es.niux.efc.demoapp.common.injection.presentation.feature.movie.list;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import es.niux.efc.demoapp.common.injection.scope.PerActivity;
import es.niux.efc.demoapp.presentation.feature.movie.list.MovieListActivityImpl;

@PerActivity
@Subcomponent(modules = {
        MovieListModule.class
})
public interface MovieListComponent extends AndroidInjector<MovieListActivityImpl> {
    @Subcomponent.Builder abstract class Builder
            extends AndroidInjector.Builder<MovieListActivityImpl>
    { }
}
