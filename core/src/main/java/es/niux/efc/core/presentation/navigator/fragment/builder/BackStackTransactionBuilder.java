package es.niux.efc.core.presentation.navigator.fragment.builder;

import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import es.niux.efc.core.common.util.Check;

@SuppressWarnings({"unused", "WeakerAccess"})
public class BackStackTransactionBuilder {
    private final @NonNull FragmentManager manager;
    private final @NonNull TransactionTarget[] targets;
    private final @NonNull String backStackName;
    private @Nullable Integer[] animations;

    public BackStackTransactionBuilder(
            @NonNull FragmentManager manager,
            @NonNull TransactionTarget[] targets,
            @NonNull String backStackName
    ) {
        this.manager = Check.nonNull(manager);
        this.targets = Check.nonNullArr(targets, false);
        this.backStackName = Check.nonNull(backStackName);
    }

    public @NonNull BackStackTransactionBuilder animate(
            @AnimatorRes @AnimRes int animResEnter, @AnimatorRes @AnimRes int animResExit,
            @AnimatorRes @AnimRes int animResPopEnter, @AnimatorRes @AnimRes int animResPopExit
    ) {
        this.animations = new Integer[]{
                animResEnter, animResExit, animResPopEnter, animResPopExit
        }; return this;
    }

    public @NonNull BackStackTransactionBuilder animate(
            @AnimatorRes @AnimRes int animResEnter, @AnimatorRes @AnimRes int animResExit
    ) {
        this.animations = new Integer[]{
                animResEnter, animResExit
        }; return this;
    }

    public @NonNull FragmentTransaction build() {
        return TransactionBuilder.buildTransaction(
                manager, animations, targets, backStackName
        );
    }

    /**
     * @return The identifier of this transaction's back stack entry,
     */
    public int commit() {
        return build().commit();
    }
}
