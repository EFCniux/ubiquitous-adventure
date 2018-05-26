package es.niux.efc.core.domain.interactor;

import android.support.annotation.NonNull;

public interface Interactor<T, P> {
    @NonNull T build(@NonNull P param);
}
