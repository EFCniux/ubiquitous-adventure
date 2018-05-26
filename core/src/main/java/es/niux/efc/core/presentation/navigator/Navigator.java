package es.niux.efc.core.presentation.navigator;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.SupportActivity;

import es.niux.efc.core.common.util.Check;

@SuppressWarnings("unused")
public abstract class Navigator implements INavigator {
    private final @NonNull SupportActivity activity;

    protected Navigator(@NonNull SupportActivity activity) {
        this.activity = Check.nonNull(activity);
    }

    protected void activityStart(@NonNull Intent activityIntent) {
        activity.startActivity(activityIntent);
    }

    protected void activityChange(@NonNull Intent activityIntent) {
        activity.startActivity(activityIntent);
        activity.finish();
    }

    protected void activityChangeTask(@NonNull Intent activityIntent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivity(activityIntent);
            activity.finishAffinity();
        } else {
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(activityIntent);
        }
    }

    protected void activityFinish() {
        activity.finish();
    }
}
