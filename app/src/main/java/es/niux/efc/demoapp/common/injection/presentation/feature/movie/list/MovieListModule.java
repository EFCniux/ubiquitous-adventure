package es.niux.efc.demoapp.common.injection.presentation.feature.movie.list;

import dagger.Binds;
import dagger.Module;
import es.niux.efc.demoapp.common.injection.scope.PerActivity;
import es.niux.efc.demoapp.presentation.feature.movie.list.MovieListActivity;
import es.niux.efc.demoapp.presentation.feature.movie.list.MovieListActivityImpl;
import es.niux.efc.demoapp.presentation.feature.movie.list.MovieListPresenter;
import es.niux.efc.demoapp.presentation.feature.movie.list.MovieListPresenterImpl;

@Module
abstract class MovieListModule {
    @Binds @PerActivity abstract MovieListActivity provideMovieListActivity(
            MovieListActivityImpl splashActivity
    );

    @Binds @PerActivity abstract MovieListPresenter provideMovieListPresenter(
            MovieListPresenterImpl movieListPresenter
    );
}
