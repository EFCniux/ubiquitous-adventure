package es.niux.efc.core.presentation.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.niux.efc.core.presentation.navigator.INavigator;

public interface IPresenter {
    /** View is being created **/
    default void  onCreate() { }
    /** View is restoring its state from a previous (now destroyed) state **/
    default void onRestoreInstance(@NonNull Bundle toRestoreState) { }
    /** View is started but not visible **/
    default void onStart() { }
    /** View is started and visible **/
    default void onResume() { }
    /** View is stopped and visible **/
    default void onPause() { }
    /** View is stopped and not visible **/
    default void onStop() { }
    /** View is saving its state for (possible) future restoration **/
    default void onSaveInstance(@NonNull Bundle toSaveState) { }
    /** View is being destroyed **/
    default void onDestroy() { }

    @Nullable INavigator getNavigator();
}