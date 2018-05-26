package es.niux.efc.demoapp;

import android.app.Activity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import es.niux.efc.core.AAplication;
import es.niux.efc.demoapp.common.injection.DaggerApplicationComponent;

public class TheApplication extends AAplication implements HasActivityInjector {
    @Inject DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
