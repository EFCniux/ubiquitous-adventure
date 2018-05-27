package es.niux.efc.demoapp.presentation.feature.movie.list;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import es.niux.efc.core.common.util.Check;
import es.niux.efc.core.common.util.Res;
import es.niux.efc.core.presentation.presenter.IPresenter;
import es.niux.efc.demoapp.R;
import es.niux.efc.demoapp.common.injection.glide.GlideApp;
import es.niux.efc.demoapp.data.entity.Movie;
import es.niux.efc.demoapp.presentation.view.activity.BaseActivity;
import es.niux.efc.demoapp.presentation.view.async.IAsyncHelper;

public class MovieListActivityImpl extends BaseActivity implements MovieListActivity {
    public static @NonNull Intent getCallingIntent(@NonNull Context context) {
        return new Intent(context, MovieListActivityImpl.class);
    }

    @Inject MovieListPresenter presenter;
    @BindView(R.id.tb_abl) Toolbar toolbar;
    @BindView(R.id.pb_abl) ProgressBar progressBar;
    @BindView(R.id.va_movie) ViewAnimator vaMovie;
    @BindView(R.id.rv_movie) RecyclerView rvMovie;
    private IAsyncHelper asyncHelper;
    private MovieListAdapter adapter;
    private AlertDialog dialogMovieError;

    @Override
    protected void onCreate(Bundle toRestoreState) {
        AndroidInjection.inject(this);
        super.onCreate(toRestoreState);
        setContentView(R.layout.movie_list_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        asyncHelper = new IAsyncHelper(this, progressBar);

        adapter = new MovieListAdapter(this, new DiffUtil.ItemCallback<Movie>() {
            @Override
            public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(Movie oldItem, Movie newItem) {
                return oldItem.getTitle().equals(newItem.getTitle())
                        && oldItem.getOverview().equals(newItem.getOverview())
                        && oldItem.getDateRelease().equals(newItem.getDateRelease())
                        && oldItem.getPicture().equals(newItem.getPicture());
            }
        });
        rvMovie.setLayoutManager(new LinearLayoutManager(
                rvMovie.getContext(), LinearLayoutManager.VERTICAL, false
        ));
        rvMovie.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialogMovieError != null) {
            dialogMovieError.dismiss();
            dialogMovieError = null;
        }
    }

    @Override
    protected @Nullable IPresenter onCreatePresenter() {
        return presenter;
    }

    @Override
    public void onAsyncStart(int asyncOperationId) {
        asyncHelper.onAsyncStart(asyncOperationId);
    }

    @Override
    public void onAsyncEnd(int asyncOperationId) {
        asyncHelper.onAsyncEnd(asyncOperationId);
    }

    @Override
    public void onAsyncError(int asyncOperationId, @NonNull Throwable e, @Nullable Runnable retry) {
        switch (asyncOperationId) {
            case MovieListPresenter.ASYNC_OPERATION_MOVIE_LIST:
                if (dialogMovieError != null) dialogMovieError.dismiss();
                dialogMovieError = asyncHelper.onCreateErrorDialog(asyncOperationId, e, retry)
                        .setOnDismissListener(dialog -> dialogMovieError = null)
                        .create();
                dialogMovieError.show();
                break;
            case MovieListPresenter.ASYNC_OPERATION_MOVIE_LIST_PAGING:
                Snackbar make = Snackbar.make(rvMovie, e.getMessage(), Snackbar.LENGTH_INDEFINITE);
                if (retry != null) make.setAction(R.string.action_failure_retry, v -> retry.run());
                make.show();
                break;
            default:
                asyncHelper.onAsyncError(asyncOperationId, e, retry);
                break;
        }
    }

    @Override
    public void onMovieList(@NonNull PagedList<Movie> movies) {
        adapter.submitList(movies);
    }


    public class MovieListAdapter extends PagedListAdapter<Movie, MovieListAdapter.ViewHolder> {
        private final @NonNull LayoutInflater inflater;
        private final @NonNull DateFormat dateFormat;

        public MovieListAdapter(
                @NonNull Context context,
                @NonNull DiffUtil.ItemCallback<Movie> diffCallback
        ) {
            super(diffCallback);
            this.inflater = LayoutInflater.from(Check.nonNull(context));
            this.dateFormat = new SimpleDateFormat("yyyy", Res.getLocales(context)[0]);
        }

        public MovieListAdapter(
                @NonNull Context context,
                @NonNull AsyncDifferConfig<Movie> config
        ) {
            super(config);
            this.inflater = LayoutInflater.from(Check.nonNull(context));
            this.dateFormat = new SimpleDateFormat("yyyy", Res.getLocales(context)[0]);
        }

        @NonNull @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(
                    R.layout.movie_list_item, parent, false
            ));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Movie item = Check.nonNull(getItem(position));

            GlideApp.with(MovieListActivityImpl.this)
                    .load(item.getPicture())
                    .placeholder(R.drawable.ic_app_color_48dp)
                    .centerCrop()
                    .into(holder.ivPicture);

            holder.tvTitle.setText(item.getTitle());
            holder.tvDate.setText(dateFormat.format(item.getDateRelease()));
            holder.tvOverview.setText(item.getOverview());
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv_movie_picture) ImageView ivPicture;
            @BindView(R.id.tv_movie_title) TextView tvTitle;
            @BindView(R.id.tv_movie_date) TextView tvDate;
            @BindView(R.id.tv_movie_overview) TextView tvOverview;
            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
