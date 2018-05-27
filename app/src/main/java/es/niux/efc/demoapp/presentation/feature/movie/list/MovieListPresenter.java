package es.niux.efc.demoapp.presentation.feature.movie.list;

import es.niux.efc.core.presentation.presenter.IPresenter;

public interface MovieListPresenter extends IPresenter {
    int ASYNC_OPERATION_MOVIE_LIST = 0;
    int ASYNC_OPERATION_MOVIE_LIST_PAGING = 1;
}
