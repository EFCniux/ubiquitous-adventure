package es.niux.efc.demoapp.presentation.view.async;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.ProgressBar;

import java.util.ArrayList;

import es.niux.efc.core.common.util.Check;
import es.niux.efc.core.presentation.view.async.IAsync;
import es.niux.efc.demoapp.R;

/**
 * A helper for {@link IAsync} to work with a {@link ProgressBar} and display simple error messages.
 */
public class IAsyncHelper implements IAsync {
    private final @NonNull Context context;
    private final @NonNull ProgressBar progressBar;
    private final @NonNull ArrayList<Integer> asyncList;

    public IAsyncHelper(
            @NonNull Context context,
            @NonNull ProgressBar progressBar
    ) {
        this.context = Check.nonNull(context);
        this.progressBar = Check.nonNull(progressBar);
        this.asyncList = new ArrayList<>();
    }

    @Override
    public void onAsyncStart(int asyncOperationId) {
        if (asyncList.isEmpty()) {
            progressBar.setIndeterminate(true);
        } asyncList.add((Integer)asyncOperationId);
    }

    @Override
    public void onAsyncEnd(int asyncOperationId) {
        if(!asyncList.contains((Integer)asyncOperationId))
        { throw new IllegalStateException("No async operation is started with id: " + asyncOperationId); }

        if (asyncList.remove((Integer)asyncOperationId)){
            if (asyncList.isEmpty()) {
                progressBar.setIndeterminate(false);
            }
        }
    }

    @Override
    public void onAsyncError(int asyncOperationId, @NonNull Throwable e, @Nullable Runnable retry) {
        onCreateErrorDialog(asyncOperationId, e, retry).create().show();
    }

    public @NonNull AlertDialog.Builder onCreateErrorDialog(
            int asyncOperationId, @NonNull Throwable throwable, @Nullable Runnable retry
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setNeutralButton(android.R.string.ok, null)
                .setTitle(throwable.getClass().getName())
                .setMessage(throwable.getLocalizedMessage());
        if (retry != null) {
            builder.setPositiveButton(R.string.action_failure_retry,
                    (dialog, which) -> retry.run()
            );
        } return builder;
    }
}
