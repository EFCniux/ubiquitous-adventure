package es.niux.efc.demoapp.domain.interactor;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.niux.efc.core.common.util.Check;
import es.niux.efc.demoapp.common.ApplicationSchedulers;
import es.niux.efc.demoapp.data.entity.Keyword;
import es.niux.efc.demoapp.data.entity.Page;
import es.niux.efc.demoapp.data.repository.KeywordRepository;
import es.niux.efc.demoapp.domain.exception.KeywordQueryException;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class SearchKeywordListInteractorImpl implements SearchKeywordListInteractor {
    private final @NonNull KeywordRepository keywordRepository;
    private final @NonNull ApplicationSchedulers schedulers;

    @Inject
    public SearchKeywordListInteractorImpl(
            @NonNull KeywordRepository keywordRepository,
            @NonNull ApplicationSchedulers schedulers
    ) {
        this.keywordRepository = Check.nonNull(keywordRepository);
        this.schedulers = Check.nonNull(schedulers);
    }

    @Override
    public @NonNull Single<List<Keyword>> build(@NonNull String param) {
        return keywordRepository
                .getKeywordsByQuery(1, param)
                .flatMap((Function<Page<Keyword>, SingleSource<List<Keyword>>>) firstKeywordPage -> {
                    if (firstKeywordPage.getTotalPages() > 1) {
                        return Observable
                                .range(2, firstKeywordPage.getTotalPages() - 1)
                                .flatMapSingle((Function<Integer, SingleSource<Page<Keyword>>>) page ->
                                        keywordRepository.getKeywordsByQuery(page, param))
                                .collectInto(
                                        new ArrayList<>(firstKeywordPage.getItems()),
                                        (keywords, nextKeywordPage) ->
                                                keywords.addAll(nextKeywordPage.getItems())
                                );
                    } else {
                        return Single
                                .just(firstKeywordPage.getItems());
                    }
                })
                .doOnSuccess(keywords -> {
                    if (keywords.isEmpty()) throw new KeywordQueryException("No keywords found!");
                })
                .subscribeOn(schedulers.io());
    }
}
