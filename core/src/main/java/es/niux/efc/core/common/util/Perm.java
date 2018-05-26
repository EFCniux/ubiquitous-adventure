package es.niux.efc.core.common.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.SimpleArrayMap;

import static android.content.pm.PackageManager.PERMISSION_DENIED;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;


/**
 * A utility class for checking and asking system permissions
 * @see <a href="https://developer.android.com/training/permissions/index.html">Working with System Permissions</a>
 */
public final class Perm {
    private Perm() { throw new UnsupportedOperationException("Nope."); }

    /**
     * @see ContextCompat#checkSelfPermission(Context, String)
     */
    public static boolean check(@NonNull Context context, @NonNull String permission) {
        return checkInternal(ContextCompat.checkSelfPermission(context, permission));
    }

    /**
     * @see ActivityCompat#shouldShowRequestPermissionRationale(Activity, String)
     */
    public static boolean shouldExplain(@NonNull Activity activity, @NonNull String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    /**
     * @see Fragment#shouldShowRequestPermissionRationale(String)
     */
    public static boolean shouldExplain(@NonNull Fragment fragment, @NonNull String permission) {
        return fragment.shouldShowRequestPermissionRationale(permission);
    }

    /**
     * @see ActivityCompat#requestPermissions(Activity, String[], int)
     */
    public static void request(
            @NonNull Activity activity, int requestCode, @NonNull String... permissions
    ) {
        ActivityCompat.requestPermissions(
                activity, Check.nonNullArr(permissions, false), requestCode
        );
    }

    /**
     * @see Fragment#requestPermissions(String[], int)
     */
    public static void request(
            @NonNull Fragment fragment, int requestCode, @NonNull String... permissions
    ) {
        fragment.requestPermissions(
                Check.nonNullArr(permissions, false), requestCode
        );
    }

    public static SimpleArrayMap<String, Boolean> checkResult(
            @NonNull String[] requestedPermissions, @NonNull int[] grantedPermissions
    ) {
        SimpleArrayMap<String, Boolean> result = new SimpleArrayMap<>();
        if (requestedPermissions.length > 0) {
            for (int i = 0, l = requestedPermissions.length; i < l; i += 1) {
                result.put(
                        requestedPermissions[i],
                        checkInternal(grantedPermissions[i])
                );
            }
        }
        return result;
    }

    private static boolean checkInternal(int result) {
        switch (result) {
            case PERMISSION_GRANTED:
                return true;
            case PERMISSION_DENIED:
                return false;
            default:
                throw new IllegalArgumentException("Permission result: " + String.valueOf(result));
        }
    }
}
