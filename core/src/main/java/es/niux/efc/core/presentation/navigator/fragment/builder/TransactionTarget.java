package es.niux.efc.core.presentation.navigator.fragment.builder;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import es.niux.efc.core.common.util.Check;

public class TransactionTarget<F extends Fragment> {
    private final int containerResId;
    private final @NonNull String tag;
    private final @NonNull F fragment;

    private TransactionTarget(
            int containerResId, @NonNull String tag, @NonNull F fragment
    ) {
        this.containerResId = containerResId;
        this.tag = Check.nonNull(tag);
        this.fragment = Check.nonNull(fragment);
    }

    public static @NonNull <F extends Fragment> TransactionTarget from(
            int containerResId, @NonNull String tag, @NonNull F fragment
    ) {
        return new TransactionTarget<>(containerResId, tag, fragment);
    }

    public int getContainerResId() {
        return containerResId;
    }

    public @NonNull String getTag() {
        return tag;
    }

    public @NonNull F getFragment() {
        return fragment;
    }
}
