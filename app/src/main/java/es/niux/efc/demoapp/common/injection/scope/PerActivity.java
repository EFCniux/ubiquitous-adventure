package es.niux.efc.demoapp.common.injection.scope;

import android.app.Activity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * A dagger2 scoping annotation for those objects whose lifetime should be that of an {@link Activity}.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {}