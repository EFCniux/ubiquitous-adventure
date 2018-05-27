package es.niux.efc.demoapp.presentation.feature.movie.list;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import es.niux.efc.core.presentation.view.activity.IActivity;
import es.niux.efc.core.presentation.view.async.IAsync;
import es.niux.efc.demoapp.data.entity.Movie;
import es.niux.efc.demoapp.presentation.view.activity.BaseActivity;

public interface MovieListActivity extends IActivity<BaseActivity>, IAsync {
    void onMovieList(@NonNull PagedList<Movie> movies);
}
