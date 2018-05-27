package es.niux.efc.demoapp.data.repository;

import android.support.annotation.NonNull;

import es.niux.efc.demoapp.data.entity.Movie;
import es.niux.efc.demoapp.data.entity.Page;
import io.reactivex.Single;

public interface MovieRepository {
    @NonNull Single<Page<Movie>> getPopularMovies(int page);
    @NonNull Single<Page<Movie>> getPopularMovies(int page, int[] idsKeyword);
}
