package es.niux.efc.demoapp.presentation.feature.movie.list;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import es.niux.efc.core.common.util.Check;
import es.niux.efc.core.domain.listener.observer.ObservableDisposableObserver;
import es.niux.efc.core.presentation.navigator.INavigator;
import es.niux.efc.demoapp.common.ApplicationSchedulers;
import es.niux.efc.demoapp.data.entity.Keyword;
import es.niux.efc.demoapp.data.entity.Movie;
import es.niux.efc.demoapp.domain.interactor.PopularMoviePagedListInteractor;
import es.niux.efc.demoapp.domain.interactor.SearchKeywordListInteractor;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class MovieListPresenterImpl implements MovieListPresenter {
    private final @NonNull MovieListActivity activity;
    private final @NonNull ApplicationSchedulers schedulers;
    private final @NonNull SearchKeywordListInteractor keywordListInteractor;
    private final @NonNull PopularMoviePagedListInteractor popularMoviePagedListInteractor;
    private @Nullable PopularMoviePagedListObserver popularMoviePagedListObserver;

    @Inject
    public MovieListPresenterImpl(
            @NonNull MovieListActivity activity,
            @NonNull ApplicationSchedulers schedulers,
            @NonNull SearchKeywordListInteractor keywordListInteractor,
            @NonNull PopularMoviePagedListInteractor popularMoviePagedListInteractor
    ) {
        this.activity = Check.nonNull(activity);
        this.schedulers = Check.nonNull(schedulers);
        this.keywordListInteractor = Check.nonNull(keywordListInteractor);
        this.popularMoviePagedListInteractor = Check.nonNull(popularMoviePagedListInteractor);
    }

    @Override
    public void onDestroy() {
        stopGetPagedMovies();
    }

    @Override
    public @Nullable INavigator getNavigator() {
        return null;
    }

    @Override
    public void onGetMovieList(@Nullable String query) {
        startGetPagedMovies(query);
    }

    private void startGetPagedMovies(@Nullable String query) {
        stopGetPagedMovies();
        if (query == null) {
            popularMoviePagedListObserver = popularMoviePagedListInteractor
                    .build(new PopularMoviePagedListInteractor.Params(
                            ASYNC_OPERATION_MOVIE_LIST_PAGING, activity, null
                    ))
                    .observeOn(schedulers.main())
                    .subscribeWith(new PopularMoviePagedListObserver(ASYNC_OPERATION_MOVIE_LIST));
        } else {
            popularMoviePagedListObserver = keywordListInteractor
                    .build(query)
                    .map((List<Keyword> keywords) -> {
                        int[] idsKeyword = new int[keywords.size()];
                        for (int i = 0, l = keywords.size(); i < l; i++) {
                            idsKeyword[i] = keywords.get(i).getId();
                        }
                        return idsKeyword;
                    })
                    .flatMapObservable((Function<int[], ObservableSource<PagedList<Movie>>>) idsKeyword -> popularMoviePagedListInteractor
                            .build(new PopularMoviePagedListInteractor.Params(
                                    ASYNC_OPERATION_MOVIE_LIST_PAGING, activity, idsKeyword
                            ))
                    )
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
            activity.onAsyncError(asyncOperationId, e, () -> {
                startGetPagedMovies(null); // todo
            });
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
