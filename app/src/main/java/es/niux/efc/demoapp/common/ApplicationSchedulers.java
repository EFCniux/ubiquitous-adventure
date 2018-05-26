package es.niux.efc.demoapp.common;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

public interface ApplicationSchedulers {
    @NonNull Scheduler single();
    @NonNull Scheduler computation();
    @NonNull Scheduler io();
    @NonNull Scheduler main();
}
