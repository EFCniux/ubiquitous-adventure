package es.niux.efc.core.domain.listener.observer;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

/**
 * @param <T>
 * @see io.reactivex.observers.DisposableMaybeObserver
 */
public abstract class MaybeDisposableObserver<T> extends Observer implements MaybeObserver<T> {
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

    @CallSuper @Override
    public void onComplete() {
        doOnEnd();
    }

    @Override
    protected void onEnd() { }
}
