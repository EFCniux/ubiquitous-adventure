package es.niux.efc.core.domain.listener.subscriber;

import android.support.annotation.NonNull;

import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicReference;

import es.niux.efc.core.common.util.Check;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.EndConsumerHelper;

public abstract class Subscriber implements Disposable {
    private boolean isStarted, isEnded;
    private final @NonNull AtomicReference<Subscription> subscription;

    Subscriber() {
        this.isStarted = false;
        this.isEnded = false;
        this.subscription = new AtomicReference<>();
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

    final boolean setSubscription(@NonNull Subscription subscription) {
        return EndConsumerHelper.setOnce(this.subscription, Check.nonNull(subscription), getClass());
    }

    //--endregion impl -----------------------------------------------------------------------------


    protected abstract void onStart();

    protected abstract void onEnd();


    /**
     *
     * @param n the request amount, positive
     * @see io.reactivex.subscribers.DisposableSubscriber#request(long)
     */
    protected final void request(long n) {
        subscription.get().request(n);
    }

    /**
     * Alias for {@link #dispose()}
     * @see io.reactivex.subscribers.DisposableSubscriber#cancel()
     */
    protected final void cancel() {
        dispose();
    }

    @Override
    public final void dispose() {
        if (SubscriptionHelper.cancel(subscription)) {
            doOnEnd();
        }
    }

    @Override
    public final boolean isDisposed() {
        return subscription.get() == SubscriptionHelper.CANCELLED;
    }
}
