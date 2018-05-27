package es.niux.efc.core.presentation.view.async;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface IAsync {
    void onAsyncStart(int asyncOperationId);
    void onAsyncEnd(int asyncOperationId);
    void onAsyncError(int asyncOperationId, @NonNull Throwable e, @Nullable Runnable retry);
}
