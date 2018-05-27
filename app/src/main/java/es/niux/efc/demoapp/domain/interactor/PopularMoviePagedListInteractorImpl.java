package es.niux.efc.demoapp.domain.interactor;

import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.arch.paging.RxPagedListBuilder;
import android.support.annotation.NonNull;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import es.niux.efc.core.common.util.Check;
import es.niux.efc.core.domain.listener.observer.SingleDisposableObserver;
import es.niux.efc.demoapp.common.ApplicationSchedulers;
import es.niux.efc.demoapp.data.entity.Movie;
import es.niux.efc.demoapp.data.entity.Page;
import es.niux.efc.demoapp.data.repository.MovieRepository;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;

public class PopularMoviePagedListInteractorImpl implements PopularMoviePagedListInteractor {
    private final @NonNull MovieRepository movieRepository;
    private final @NonNull ApplicationSchedulers schedulers;

    @Inject
    public PopularMoviePagedListInteractorImpl(
            @NonNull MovieRepository movieRepository,
            @NonNull ApplicationSchedulers schedulers
    ) {
        this.movieRepository = Check.nonNull(movieRepository);
        this.schedulers = Check.nonNull(schedulers);
    }

    @NonNull @Override
    public Observable<PagedList<Movie>> build(@NonNull Params params) {
        CompositeDisposable disposables = new CompositeDisposable();
        return new RxPagedListBuilder<>
                (new MoviePageKeyedDataSourceFactory(
                        params,
                        movieRepository,
                        schedulers,
                        disposables
                ), new PagedList.Config.Builder()
                        .setPageSize(20)
                        .setInitialLoadSizeHint(40)
                        .setPrefetchDistance(10)
                        .setEnablePlaceholders(false)
                        .build()
                )
                .setFetchScheduler(schedulers.io())
                .buildObservable()
                .doOnDispose(disposables::dispose);
    }

    private static class MoviePageKeyedDataSourceFactory extends DataSource.Factory<Integer, Movie> {
        private final @NonNull Params buildParams;
        private final @NonNull MovieRepository movieRepository;
        private final @NonNull ApplicationSchedulers schedulers;
        private final @NonNull CompositeDisposable disposables;

        private MoviePageKeyedDataSourceFactory(
                @NonNull Params buildParams,
                @NonNull MovieRepository movieRepository,
                @NonNull ApplicationSchedulers schedulers,
                @NonNull CompositeDisposable disposables
        ) {
            this.buildParams = Check.nonNull(buildParams);
            this.movieRepository = Check.nonNull(movieRepository);
            this.schedulers = Check.nonNull(schedulers);
            this.disposables = Check.nonNull(disposables);
        }

        @Override
        public DataSource<Integer, Movie> create() {
            return new MoviePageKeyedDataSource(buildParams, movieRepository, schedulers, disposables);
        }
    }

    private static class MoviePageKeyedDataSource extends PageKeyedDataSource<Integer, Movie> {
        private final @NonNull Params buildParams;
        private final @NonNull MovieRepository movieRepository;
        private final @NonNull ApplicationSchedulers schedulers;
        private final @NonNull CompositeDisposable disposables;

        private MoviePageKeyedDataSource(
                @NonNull Params buildParams,
                @NonNull MovieRepository movieRepository,
                @NonNull ApplicationSchedulers schedulers,
                @NonNull CompositeDisposable disposables
        ) {
            this.buildParams = Check.nonNull(buildParams);
            this.movieRepository = Check.nonNull(movieRepository);
            this.schedulers = Check.nonNull(schedulers);
            this.disposables = Check.nonNull(disposables);
        }

        @Override
        public void loadInitial(
                @NonNull LoadInitialParams<Integer> params,
                @NonNull LoadInitialCallback<Integer, Movie> callback
        ) {
            disposables.add(Single
                    .defer((Callable<SingleSource<Page<Movie>>>) () -> {
                        if (buildParams.idsKeyword != null) {
                            return movieRepository
                                    .getPopularMovies(1, buildParams.idsKeyword);
                        } else {
                            return movieRepository
                                    .getPopularMovies(1);
                        }
                    })
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.main())
                    .subscribeWith(new MoviePageKeyedObserver(buildParams) {
                        @Override
                        public void onSuccess(@NonNull Page<Movie> moviePage) {
                            super.onSuccess(moviePage);
                            callback.onResult(moviePage.getItems(),
                                    0, moviePage.getTotalResults(),
                                    null,
                                    moviePage.getPage() + 1
                            );
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            super.onError(e);
                            buildParams.async.onAsyncError(
                                    buildParams.asyncOperationId, e,
                                    () -> loadInitial(params, callback)
                            );
                        }
                    }));
        }

        @Override
        public void loadBefore(
                @NonNull LoadParams<Integer> params,
                @NonNull LoadCallback<Integer, Movie> callback
        ) {
            disposables.add(Single
                    .defer((Callable<SingleSource<Page<Movie>>>) () -> {
                        if (buildParams.idsKeyword != null) {
                            return movieRepository
                                    .getPopularMovies(params.key, buildParams.idsKeyword);
                        } else {
                            return movieRepository
                                    .getPopularMovies(params.key);
                        }
                    })
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.main())
                    .subscribeWith(new MoviePageKeyedObserver(buildParams) {
                        @Override
                        public void onSuccess(@NonNull Page<Movie> moviePage) {
                            super.onSuccess(moviePage);
                            callback.onResult(moviePage.getItems(),
                                    moviePage.getPage() - 1
                            );
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            super.onError(e);
                            buildParams.async.onAsyncError(
                                    buildParams.asyncOperationId, e,
                                    () -> loadBefore(params, callback)
                            );
                        }
                    }));
        }

        @Override
        public void loadAfter(
                @NonNull LoadParams<Integer> params,
                @NonNull LoadCallback<Integer, Movie> callback
        ) {
            disposables.add(Single
                    .defer((Callable<SingleSource<Page<Movie>>>) () -> {
                        if (buildParams.idsKeyword != null) {
                            return movieRepository
                                    .getPopularMovies(params.key, buildParams.idsKeyword);
                        } else {
                            return movieRepository
                                    .getPopularMovies(params.key);
                        }
                    })
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.main())
                    .subscribeWith(new MoviePageKeyedObserver(buildParams) {
                        @Override
                        public void onSuccess(@NonNull Page<Movie> moviePage) {
                            super.onSuccess(moviePage);
                            callback.onResult(moviePage.getItems(),
                                    moviePage.getPage() + 1
                            );
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            super.onError(e);
                            buildParams.async.onAsyncError(
                                    buildParams.asyncOperationId, e,
                                    () -> loadAfter(params, callback)
                            );
                        }
                    }));
        }
    }

    private static class MoviePageKeyedObserver extends SingleDisposableObserver<Page<Movie>> {
        private final @NonNull Params buildParams;

        private MoviePageKeyedObserver(@NonNull Params buildParams) {
            this.buildParams = Check.nonNull(buildParams);
        }

        @Override
        protected void onStart() {
            super.onStart();
            buildParams.async.onAsyncStart(buildParams.asyncOperationId);
        }

        @Override
        protected void onEnd() {
            super.onEnd();
            buildParams.async.onAsyncEnd(buildParams.asyncOperationId);
        }
    }
}
