package es.niux.efc.demoapp.common.injection.scope;

import android.app.Fragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * A dagger2 scoping annotation for those objects whose lifetime should be that of a {@link Fragment}.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {}