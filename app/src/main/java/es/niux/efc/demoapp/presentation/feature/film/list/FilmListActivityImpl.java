package es.niux.efc.demoapp.presentation.feature.film.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ViewAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import es.niux.efc.core.presentation.presenter.IPresenter;
import es.niux.efc.demoapp.R;
import es.niux.efc.demoapp.presentation.view.activity.BaseActivity;

public class FilmListActivityImpl extends BaseActivity implements FilmListActivity {
    public static @NonNull Intent getCallingIntent(@NonNull Context context) {
        return new Intent(context, FilmListActivityImpl.class);
    }

    @BindView(R.id.tb_abl) Toolbar toolbar;
    @BindView(R.id.va_main_films) ViewAnimator vaFilms;
    @BindView(R.id.rv_main_films) RecyclerView rvFilms;

    @Override
    protected void onCreate(Bundle toRestoreState) {
        AndroidInjection.inject(this);
        super.onCreate(toRestoreState);
        setContentView(R.layout.film_list_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    protected @Nullable IPresenter onCreatePresenter() {
        return null;
    }

    @Override
    public void onAsyncStart(int asyncOperationId) {
        // todo
    }

    @Override
    public void onAsyncStop(int asyncOperationId) {
        // todo
    }

    @Override
    public void onAsyncError(int asyncOperationId, @NonNull Throwable throwable) {
        // todo
    }
}
