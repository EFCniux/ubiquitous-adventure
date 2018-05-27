package es.niux.efc.demoapp.data.source.network.themoviedb;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.niux.efc.demoapp.data.source.network.themoviedb.response.DiscoverMoviesResponse;
import es.niux.efc.demoapp.data.source.network.themoviedb.response.SearchKeywordsResponse;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDbNetworkDataSource {
    @GET("discover/movie")
    @NonNull Single<Response<DiscoverMoviesResponse>> discoverMovies(
            @Query("api_key") @NonNull String apiKey,
            @Query("sort_by") @NonNull String sortBy,
            @Query("page") int page,
            @Query("with_keywords") @Nullable int[] idsKeyWord
    );

    @GET("search/keyword")
    @NonNull Single<Response<SearchKeywordsResponse>> searchKeywords(
            @Query("api_key") @NonNull String apiKey,
            @Query("query") @NonNull String query,
            @Query("page") int page
    );
}
