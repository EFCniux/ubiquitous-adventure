package es.niux.efc.demoapp.presentation.feature.movie.list;

import android.support.annotation.Nullable;

import es.niux.efc.core.presentation.presenter.IPresenter;

public interface MovieListPresenter extends IPresenter {
    int ASYNC_OPERATION_MOVIE_LIST = 0;
    int ASYNC_OPERATION_MOVIE_LIST_PAGING = 1;

    void onGetMovieList(@Nullable String query);
}
