package es.niux.efc.demoapp.presentation.feature.movie.list;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import es.niux.efc.core.common.util.Check;
import es.niux.efc.core.domain.listener.observer.ObservableDisposableObserver;
import es.niux.efc.core.presentation.navigator.INavigator;
import es.niux.efc.demoapp.common.ApplicationSchedulers;
import es.niux.efc.demoapp.data.entity.Movie;
import es.niux.efc.demoapp.domain.interactor.PopularMoviePagedListInteractor;

public class MovieListPresenterImpl implements MovieListPresenter {
    private final @NonNull MovieListActivity activity;
    private final @NonNull ApplicationSchedulers schedulers;
    private final @NonNull PopularMoviePagedListInteractor popularMoviePagedListInteractor;
    private @Nullable PopularMoviePagedListObserver popularMoviePagedListObserver;

    @Inject
    public MovieListPresenterImpl(
            @NonNull MovieListActivity activity,
            @NonNull ApplicationSchedulers schedulers,
            @NonNull PopularMoviePagedListInteractor popularMoviePagedListInteractor
    ) {
        this.activity = Check.nonNull(activity);
        this.schedulers = Check.nonNull(schedulers);
        this.popularMoviePagedListInteractor = Check.nonNull(popularMoviePagedListInteractor);
    }

    @Override
    public void onStart() {
        startGetPagedMovies();
    }

    @Override
    public void onDestroy() {
        stopGetPagedMovies();
    }

    @Override
    public @Nullable INavigator getNavigator() {
        return null;
    }

    private void startGetPagedMovies() {
        stopGetPagedMovies();
        if (popularMoviePagedListObserver == null) {
            popularMoviePagedListObserver = popularMoviePagedListInteractor
                    .build(new PopularMoviePagedListInteractor.Params(ASYNC_OPERATION_MOVIE_LIST_PAGING, activity))
                    .observeOn(schedulers.main())
                    .subscribeWith(new PopularMoviePagedListObserver(ASYNC_OPERATION_MOVIE_LIST));
        }
    }

    private void stopGetPagedMovies() {
        if (popularMoviePagedListObserver != null) {
            popularMoviePagedListObserver.dispose();
            popularMoviePagedListObserver = null;
        }
    }

    private class PopularMoviePagedListObserver extends ObservableDisposableObserver<PagedList<Movie>> {
        private final int asyncOperationId;
        private boolean hasNext = false;
        private PopularMoviePagedListObserver(int asyncOperationId) {
            this.asyncOperationId = asyncOperationId;
        }

        @Override
        protected void onStart() {
            super.onStart();
            activity.onAsyncStart(asyncOperationId);
        }

        @Override
        public void onNext(@NonNull PagedList<Movie> movies) {
            super.onNext(movies);
            if (!hasNext) {
                activity.onAsyncEnd(asyncOperationId);
                hasNext = true;
            }
            activity.onMovieList(movies);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            super.onError(e);
            activity.onAsyncError(asyncOperationId, e,
                    MovieListPresenterImpl.this::startGetPagedMovies
            );
        }

        @Override
        public void onComplete() {
            super.onComplete();
        }

        @Override
        protected void onEnd() {
            super.onEnd();
            if (!hasNext) activity.onAsyncEnd(asyncOperationId);
        }
    }
}
