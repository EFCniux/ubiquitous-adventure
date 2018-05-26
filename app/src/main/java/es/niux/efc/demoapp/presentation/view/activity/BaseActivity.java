package es.niux.efc.demoapp.presentation.view.activity;

import android.support.annotation.NonNull;

import es.niux.efc.core.presentation.view.activity.Activity;
import es.niux.efc.core.presentation.view.activity.IActivity;

public abstract class BaseActivity extends Activity implements IActivity<BaseActivity> {
    @Override
    public @NonNull BaseActivity getActivity() {
        return this;
    }
}
