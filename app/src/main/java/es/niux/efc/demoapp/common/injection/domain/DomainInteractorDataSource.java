package es.niux.efc.demoapp.common.injection.domain;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.niux.efc.demoapp.domain.interactor.PopularMoviePagedListInteractor;
import es.niux.efc.demoapp.domain.interactor.PopularMoviePagedListInteractorImpl;
import es.niux.efc.demoapp.domain.interactor.SearchKeywordListInteractor;
import es.niux.efc.demoapp.domain.interactor.SearchKeywordListInteractorImpl;

@Module
public class DomainInteractorDataSource {
    @Provides @Singleton  PopularMoviePagedListInteractor providePopularMoviePagedListInteractor(
            PopularMoviePagedListInteractorImpl popularMoviePagedListInteractor
    ) {
        return popularMoviePagedListInteractor;
    }

    @Provides @Singleton SearchKeywordListInteractor provideSearchKeywordListInteractor(
            SearchKeywordListInteractorImpl searchKeywordListInteractor
    ) {
        return searchKeywordListInteractor;
    }
}
