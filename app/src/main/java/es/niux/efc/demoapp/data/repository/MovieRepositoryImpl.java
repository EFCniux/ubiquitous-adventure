package es.niux.efc.demoapp.data.repository;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.niux.efc.core.common.util.Check;
import es.niux.efc.core.data.exception.ExponentialBackoffFunction;
import es.niux.efc.demoapp.BuildConfig;
import es.niux.efc.demoapp.data.entity.Movie;
import es.niux.efc.demoapp.data.entity.Page;
import es.niux.efc.demoapp.data.source.network.ResponseMapFunction;
import es.niux.efc.demoapp.data.source.network.themoviedb.TheMovieDbNetworkDataSource;
import es.niux.efc.demoapp.data.source.network.themoviedb.response.DiscoverMoviesResponse;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import retrofit2.Response;

// todo: add room db as cache
public class MovieRepositoryImpl implements MovieRepository {
    private final @NonNull TheMovieDbNetworkDataSource movieApiDataSource;

    @Inject
    public MovieRepositoryImpl(
            @NonNull TheMovieDbNetworkDataSource movieApiDataSource
    ) {
        this.movieApiDataSource = Check.nonNull(movieApiDataSource);
    }

    @NonNull @Override
    public Single<Page<Movie>> getPopularMovies(int page) {
        return getPopularMovies(movieApiDataSource
                .discoverMovies(BuildConfig.TMDB_API_KEY,
                        "popularity.desc", page, null
                )
                .retryWhen(ExponentialBackoffFunction.with(4, IOException.class)));
    }

    @NonNull @Override
    public Single<Page<Movie>> getPopularMovies(int page, @NonNull int[] idsKeyword) {
        return Single
                .fromCallable(() -> {
                    String idsKeywordOr = "";
                    for (int i = 0, l = idsKeyword.length; i < l; i++) {
                        int id = idsKeyword[i];
                        idsKeywordOr += i == 0 ? Integer.valueOf(id) : "|" + id;
                    } return idsKeywordOr;
                })
                .flatMap((Function<String, SingleSource<Page<Movie>>>) idsKeywordOr ->
                        getPopularMovies(movieApiDataSource
                                .discoverMovies(BuildConfig.TMDB_API_KEY,
                                        "popularity.desc", page, idsKeywordOr
                                )
                                .retryWhen(ExponentialBackoffFunction.with(4, IOException.class))
                        )
                );
    }


    private Single<Page<Movie>> getPopularMovies(
            @NonNull Single<Response<DiscoverMoviesResponse>> responseSingle
    ) {
        return responseSingle
                .map(new ResponseMapFunction<>())
                .map(response -> {
                    List<Movie> movies = new ArrayList<>(response.results.length);
                    for (DiscoverMoviesResponse.Result result : response.results) {
                        movies.add(new Movie(
                                result.id,
                                result.title,
                                result.overview,
                                Uri.parse("https://image.tmdb.org/t/p/original/" + result.picturePath),
                                result.dateRelease
                        ));
                    }
                    return new Page<>(
                            response.page,
                            response.totalResults,
                            response.totalPages,
                            movies
                    );
                });
    }
}
