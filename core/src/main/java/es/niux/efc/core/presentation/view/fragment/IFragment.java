package es.niux.efc.core.presentation.view.fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import es.niux.efc.core.presentation.view.activity.IActivity;


public interface IFragment<A extends IActivity<? extends FragmentActivity>, F extends Fragment> {
    @NonNull A getParentActivity();
    @NonNull F getFragment();
}
