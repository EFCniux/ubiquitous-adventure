package es.niux.efc.demoapp.presentation.view.fragment;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import es.niux.efc.core.presentation.view.activity.IActivity;
import es.niux.efc.core.presentation.view.fragment.Fragment;
import es.niux.efc.core.presentation.view.fragment.IFragment;

public abstract class BaseFragment<A extends IActivity<? extends FragmentActivity>>
        extends Fragment implements IFragment<A, BaseFragment>
{
    private @Nullable A activity;

    @CallSuper @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            //noinspection unchecked
            this.activity = (A)context;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Illegal fragment context!", e);
        }
    }

    @Override
    public @NonNull A getParentActivity() {
        if (activity == null) {
            throw new IllegalStateException("Fragment not yet attacked to an activity!");
        } return activity;
    }

    @Override
    public @NonNull BaseFragment getFragment() {
        return this;
    }
}