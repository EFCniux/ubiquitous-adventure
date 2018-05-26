package es.niux.efc.core.domain.listener.observer;


import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

/**
 * @see io.reactivex.observers.DisposableCompletableObserver
 */
public abstract class CompletableDisposableObserver extends Observer implements CompletableObserver {
    @Override
    public final void onSubscribe(@NonNull Disposable d) {
        if (setDisposable(d)) {
            doOnStart();
        }
    }

    @Override
    protected void onStart() { }

    @CallSuper @Override
    public void onComplete() {
        doOnEnd();
    }

    @CallSuper @Override
    public void onError(@NonNull Throwable e) {
        doOnEnd();
    }

    @Override
    protected void onEnd() { }
}
