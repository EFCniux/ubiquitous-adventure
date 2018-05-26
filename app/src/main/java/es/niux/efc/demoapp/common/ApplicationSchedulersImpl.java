package es.niux.efc.demoapp.common;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ApplicationSchedulersImpl implements ApplicationSchedulers {
    @Inject
    public ApplicationSchedulersImpl() {}

    @Override
    public @NonNull Scheduler single() {
        return io.reactivex.schedulers.Schedulers.single();
    }

    @Override
    public @NonNull Scheduler computation() {
        return io.reactivex.schedulers.Schedulers.computation();
    }

    @Override
    public @NonNull Scheduler io() {
        return io.reactivex.schedulers.Schedulers.io();
    }

    @Override
    public @NonNull Scheduler main() {
        return AndroidSchedulers.mainThread();
    }
}
