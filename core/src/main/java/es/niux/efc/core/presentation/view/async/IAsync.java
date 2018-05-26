package es.niux.efc.core.presentation.view.async;

import android.support.annotation.NonNull;

public interface IAsync {
    void onAsyncStart(int asyncOperationId);
    void onAsyncStop(int asyncOperationId);
    void onAsyncError(int asyncOperationId, @NonNull Throwable throwable);
}
