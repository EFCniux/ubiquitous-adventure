package es.niux.efc.demoapp.data.repository;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.niux.efc.core.data.exception.ExponentialBackoffFunction;
import es.niux.efc.demoapp.BuildConfig;
import es.niux.efc.demoapp.data.entity.Movie;
import es.niux.efc.demoapp.data.entity.Page;
import es.niux.efc.demoapp.data.source.network.ResponseMapFunction;
import es.niux.efc.demoapp.data.source.network.themoviedb.TheMovieDbNetworkDataSource;
import es.niux.efc.demoapp.data.source.network.themoviedb.response.DiscoverMoviesResponse;
import io.reactivex.Single;

public class MovieRepositoryImpl implements MovieRepository {
    private final @NonNull TheMovieDbNetworkDataSource movieApiDataSource;

    @Inject
    public MovieRepositoryImpl(
            @NonNull TheMovieDbNetworkDataSource movieApiDataSource
    ) {
        this.movieApiDataSource = movieApiDataSource;
    }

    @NonNull @Override
    public Single<Page<Movie>> getPopularMovies(int page) {
        return movieApiDataSource
                .discoverMovies(BuildConfig.TMDB_API_KEY, "popularity.desc", page, null)
                .retryWhen(ExponentialBackoffFunction.with(4, IOException.class))
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

    @NonNull @Override
    public Single<Page<Movie>> getPopularMovies(int page, int[] idsKeyword) {
        return null; // todo
    }
}
