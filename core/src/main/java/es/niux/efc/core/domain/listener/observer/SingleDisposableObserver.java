package es.niux.efc.core.domain.listener.observer;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import io.reactivex.disposables.Disposable;

/**
 * @param <T>
 * @see io.reactivex.observers.DisposableSingleObserver
 */
public abstract class SingleDisposableObserver<T> extends Observer implements io.reactivex.SingleObserver<T> {
    @Override
    public final void onSubscribe(@NonNull Disposable d) {
        if (setDisposable(d)) {
            doOnStart();
        }
    }

    @Override
    protected void onStart() { }

    @CallSuper @Override
    public void onSuccess(@NonNull T t) {
        doOnEnd();
    }

    @CallSuper @Override
    public void onError(@NonNull Throwable e) {
        doOnEnd();
    }

    @Override
    protected void onEnd() { }
}
