package es.niux.efc.core.presentation.view.activity;

import android.support.annotation.NonNull;
import android.support.v4.app.SupportActivity;

public interface IActivity<A extends SupportActivity> {
    @NonNull A getActivity();
}
