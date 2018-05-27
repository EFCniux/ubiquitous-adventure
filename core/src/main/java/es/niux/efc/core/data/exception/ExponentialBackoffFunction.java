package es.niux.efc.core.data.exception;

import android.support.annotation.NonNull;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import es.niux.efc.core.common.util.Check;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class ExponentialBackoffFunction<T extends Throwable> implements Function<Flowable<Throwable>, Publisher<?>> {
    private final Class<T> throwableClazz;
    private final long delayMilliseconds;
    private final int maxAttempts;
    private int numAttempts;

    private ExponentialBackoffFunction(int maxAttempts, @NonNull Class<T> throwableClazz, long delayMilliseconds) {
        this.delayMilliseconds = delayMilliseconds;
        this.throwableClazz = Check.nonNull(throwableClazz);
        this.maxAttempts = maxAttempts;
        this.numAttempts = 0;
    }

    public static ExponentialBackoffFunction<Throwable> with(
            int maxAttempts
    ) {
        return with(maxAttempts, Throwable.class);
    }

    public static <T extends Throwable> ExponentialBackoffFunction<T> with(
            int maxAttempts, @NonNull Class<T> throwableClazz
    ) {
        return with(maxAttempts, throwableClazz, TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS));
    }

    public static <T extends Throwable> ExponentialBackoffFunction<T> with(
            int maxAttempts, @NonNull Class<T> throwableClazz, long delayMilliseconds
    ) {
        return new ExponentialBackoffFunction<>(maxAttempts, throwableClazz, delayMilliseconds);
    }

    @Override
    public Publisher<?> apply(Flowable<Throwable> throwableFlowable) throws Exception {
        return throwableFlowable
                .flatMap((Function<Throwable, Publisher<?>>) (Throwable throwable) -> {
                    numAttempts += 1;
                    if (throwableClazz.isAssignableFrom(throwable.getClass()) && numAttempts < maxAttempts) {
                        return Flowable
                                .timer(delayMilliseconds * numAttempts, TimeUnit.MILLISECONDS);
                    } else {
                        return Flowable
                                .error(throwable);
                    }
                });
    }
}
