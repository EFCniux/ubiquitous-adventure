package es.niux.efc.demoapp.data.repository;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.niux.efc.core.common.util.Check;
import es.niux.efc.core.data.exception.ExponentialBackoffFunction;
import es.niux.efc.demoapp.BuildConfig;
import es.niux.efc.demoapp.data.entity.Keyword;
import es.niux.efc.demoapp.data.entity.Page;
import es.niux.efc.demoapp.data.source.network.ResponseMapFunction;
import es.niux.efc.demoapp.data.source.network.themoviedb.TheMovieDbNetworkDataSource;
import es.niux.efc.demoapp.data.source.network.themoviedb.response.SearchKeywordsResponse;
import io.reactivex.Single;

public class KeywordRepositoryImpl implements KeywordRepository {
    private final @NonNull TheMovieDbNetworkDataSource movieApiDataSource;

    @Inject
    public KeywordRepositoryImpl(
            @NonNull TheMovieDbNetworkDataSource movieApiDataSource
    ) {
        this.movieApiDataSource = Check.nonNull(movieApiDataSource);
    }

    @Override
    public @NonNull Single<Page<Keyword>> getKeywordsByQuery(int page, @NonNull String query) {
        return movieApiDataSource
                .searchKeywords(BuildConfig.TMDB_API_KEY,
                        query, page
                )
                .retryWhen(ExponentialBackoffFunction.with(4, IOException.class))
                .map(new ResponseMapFunction<>())
                .map(response -> {
                    List<Keyword> keywords = new ArrayList<>(response.results.length);
                    for (SearchKeywordsResponse.Result result : response.results) {
                        keywords.add(new Keyword(
                                result.id,
                                result.name
                        ));
                    }
                    return new Page<>(
                            response.page,
                            response.totalResults,
                            response.totalPages,
                            keywords
                    );
                });
    }
}
