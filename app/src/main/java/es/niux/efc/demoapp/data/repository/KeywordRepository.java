package es.niux.efc.demoapp.data.repository;

import android.support.annotation.NonNull;

import es.niux.efc.demoapp.data.entity.Keyword;
import es.niux.efc.demoapp.data.entity.Page;
import io.reactivex.Single;

public interface KeywordRepository {
    @NonNull Single<Page<Keyword>> getKeywordsByQuery(int page, @NonNull String query);
}
