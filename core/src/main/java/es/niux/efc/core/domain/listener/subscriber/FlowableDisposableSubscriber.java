package es.niux.efc.core.domain.listener.subscriber;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import org.reactivestreams.Subscription;

/**
 * @param <T>
 * @see io.reactivex.subscribers.DisposableSubscriber
 */
public abstract class FlowableDisposableSubscriber<T> extends Subscriber implements io.reactivex.FlowableSubscriber<T> {
    @Override
    public final void onSubscribe(@NonNull Subscription s) {
        if (setSubscription(s)) {
            doOnStart();
        }
    }

    /**
     * Remember to call {@link Subscriber#request(long)} data when data is needed.
     *
     * @see #onSubscribe(Subscription)
     * @see FlowableDisposableSubscriber#request(long)
     * @see io.reactivex.subscribers.DisposableSubscriber#onStart()
     */
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
