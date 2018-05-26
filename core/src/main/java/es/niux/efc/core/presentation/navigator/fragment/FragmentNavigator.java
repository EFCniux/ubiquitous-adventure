package es.niux.efc.core.presentation.navigator.fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import es.niux.efc.core.presentation.navigator.Navigator;
import es.niux.efc.core.presentation.navigator.fragment.builder.BackStackTransactionBuilder;
import es.niux.efc.core.presentation.navigator.fragment.builder.TransactionBuilder;
import es.niux.efc.core.presentation.navigator.fragment.builder.TransactionTarget;

@SuppressWarnings("unused")
public abstract class FragmentNavigator extends Navigator {
    private final @NonNull FragmentActivity activity;

    protected FragmentNavigator(@NonNull FragmentActivity activity) {
        super(activity);
        activity.getSupportFragmentManager().addOnBackStackChangedListener(this::onFragmentBackStackChanged);
        this.activity = activity;
    }

    /**
     * @see FragmentManager.OnBackStackChangedListener
     */
    protected void onFragmentBackStackChanged() { /* Override */ }

    protected @NonNull TransactionBuilder fragmentReplace(
            @NonNull TransactionTarget... targets
    ) {
        return new TransactionBuilder(activity.getSupportFragmentManager(), targets);
    }

    protected @NonNull BackStackTransactionBuilder fragmentReplace(
            @NonNull String backStackName,
            @NonNull TransactionTarget... targets
    ) {
        return new BackStackTransactionBuilder(activity.getSupportFragmentManager(), targets, backStackName);
    }

    /**
     * @see FragmentManager#popBackStack(String, int)
     */
    protected void fragmentReturn(@NonNull String backStackName) {
        activity.getSupportFragmentManager().popBackStack(
                backStackName, 0
        );
    }

    /**
     * @see FragmentManager#popBackStack(String, int)
     * @see FragmentManager#POP_BACK_STACK_INCLUSIVE
     */
    protected void fragmentRemove(@NonNull String backStackName) {
        activity.getSupportFragmentManager().popBackStack(
                backStackName, FragmentManager.POP_BACK_STACK_INCLUSIVE
        );
    }
}
