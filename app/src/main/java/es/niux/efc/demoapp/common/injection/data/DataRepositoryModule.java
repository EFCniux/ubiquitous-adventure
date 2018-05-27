package es.niux.efc.demoapp.common.injection.data;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.niux.efc.demoapp.data.repository.MovieRepository;
import es.niux.efc.demoapp.data.repository.MovieRepositoryImpl;

@Module
public class DataRepositoryModule {
    @Provides @Singleton MovieRepository provideMovieRepository(
            MovieRepositoryImpl movieRepository
    ) {
        return movieRepository;
    }
}
