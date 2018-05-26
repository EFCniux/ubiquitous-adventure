package es.niux.efc.core.domain.listener.observer;

import android.support.annotation.NonNull;

import java.util.concurrent.atomic.AtomicReference;

import es.niux.efc.core.common.util.Check;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;

public abstract class Observer implements Disposable {
    private boolean isStarted, isEnded;
    private final @NonNull AtomicReference<Disposable> disposable;

    Observer() {
        this.isStarted = false;
        this.isEnded = false;
        this.disposable = new AtomicReference<>();
    }

    //--region impl --------------------------------------------------------------------------------

    final void doOnStart() {
        if (!isStarted) {
            isStarted = true;
            onStart();
        }
    }

    final void doOnEnd() {
        if (!isEnded) {
            isEnded = true;
            onEnd();
        }
    }

    final boolean setDisposable(@NonNull Disposable disposable) {
        return EndConsumerHelper.setOnce(this.disposable,  Check.nonNull(disposable), getClass());
    }

    //--endregion impl -----------------------------------------------------------------------------


    protected abstract void onStart();

    protected abstract void onEnd();


    @Override
    public final void dispose() {
        if (DisposableHelper.dispose(disposable)) {
            doOnEnd();
        }
    }

    @Override
    public final boolean isDisposed() {
        return disposable.get() == DisposableHelper.DISPOSED;
    }
}
