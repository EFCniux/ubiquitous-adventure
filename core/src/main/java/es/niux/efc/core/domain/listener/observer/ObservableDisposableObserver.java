package es.niux.efc.core.domain.listener.observer;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import io.reactivex.disposables.Disposable;

/**
 * @param <T>
 * @see io.reactivex.observers.DisposableObserver
 */
public class ObservableDisposableObserver<T> extends Observer implements io.reactivex.Observer<T> {
    @Override
    public final void onSubscribe(@NonNull Disposable d) {
        if (setDisposable(d)) {
            doOnStart();
        }
    }

    @Override
    protected void onStart() { }

    @Override
    public void onNext(@NonNull T t) { }

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
