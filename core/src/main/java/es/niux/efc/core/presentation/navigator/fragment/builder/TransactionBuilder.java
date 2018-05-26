package es.niux.efc.core.presentation.navigator.fragment.builder;

import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import es.niux.efc.core.common.util.Check;

@SuppressWarnings({"unused", "WeakerAccess"})
public class TransactionBuilder {
    private final @NonNull FragmentManager manager;
    private final @NonNull TransactionTarget[] targets;
    private @Nullable Integer[] animations;

    public TransactionBuilder(
            @NonNull FragmentManager manager,
            @NonNull TransactionTarget[] targets
    ) {
        this.manager = Check.nonNull(manager);
        this.targets = Check.nonNullArr(targets, false);
    }

    public @NonNull TransactionBuilder animate(
            @AnimatorRes @AnimRes int animResEnter,
            @AnimatorRes @AnimRes int animResExit
    ) {
        this.animations = new Integer[]{
                animResEnter, animResExit
        }; return this;
    }

    public @NonNull FragmentTransaction build() {
        return buildTransaction(
                manager, animations, targets, null
        );
    }

    public void commit() {
        build().commit();
    }

    public void commitNow() {
        build().commitNow();
    }


    static @NonNull FragmentTransaction buildTransaction(
            @NonNull FragmentManager manager,
            @Nullable Integer[] animations,
            @NonNull TransactionTarget[] targets,
            @Nullable String backStackName
    ) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (animations != null) {
            if (animations.length == 4 && backStackName != null) {
                transaction.setCustomAnimations(
                        animations[0], animations[1],
                        animations[2], animations[3]
                );
            } else if (animations.length == 2) {
                transaction.setCustomAnimations(
                        animations[0], animations[1]
                );
            }
        }
        for (TransactionTarget target : targets) {
            transaction.replace(
                    target.getContainerResId(),
                    target.getFragment(),
                    target.getTag()
            );
        }
        if (backStackName != null) {
            transaction.addToBackStack(backStackName);
        }
        return transaction;
    }
}
