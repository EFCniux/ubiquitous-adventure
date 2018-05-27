package es.niux.efc.demoapp.domain.interactor;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.niux.efc.core.common.util.Check;
import es.niux.efc.core.domain.interactor.ObservableInteractor;
import es.niux.efc.core.presentation.view.async.IAsync;
import es.niux.efc.demoapp.data.entity.Movie;

public interface PopularMoviePagedListInteractor
        extends ObservableInteractor<PagedList<Movie>, PopularMoviePagedListInteractor.Params>
{
    final class Params {
        public final int asyncOperationId;
        public final @NonNull IAsync async;
        public final @Nullable int[] idsKeyword;

        public Params(
                int asyncOperationId, @NonNull IAsync async,
                @Nullable int[] idsKeyword
        ) {
            this.asyncOperationId = asyncOperationId;
            this.async = Check.nonNull(async);
            this.idsKeyword = idsKeyword;
        }
    }
}
