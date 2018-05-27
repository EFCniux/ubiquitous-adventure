package es.niux.efc.core.common.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A utility class to aggressively check against invalid arguments.
 * <br>It's better to crash, and see the problem, than to continue with invalid data.
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue", "SameParameterValue"})
public final class Check {
    private Check() { throw new UnsupportedOperationException("Nope."); }

    //--region Bool---------------------------------------------------------------------------------

    public static boolean nonFalse(boolean truth) {
        return nonFalse(truth, null);
    }

    public static boolean nonFalse(boolean truth, @Nullable String reason) {
        if (!truth) {
            throw new IllegalArgumentException(reason);
        } return true;
    }

    public static @NonNull <T> T nonFalse(boolean truth, @NonNull T obj) {
        return nonFalse(truth, obj, null);
    }

    public static @NonNull <T> T nonFalse(boolean truth, @NonNull T obj, @Nullable String reason) {
        if (!truth) {
            throw new IllegalArgumentException(reason);
        } return obj;
    }

    //--endregion Bool------------------------------------------------------------------------------
    //--region Object-------------------------------------------------------------------------------

    public static @NonNull <T> T nonNull(@Nullable T obj) {
        return nonNull(obj, null);
    }

    public static @NonNull <T> T nonNull(@Nullable T obj, @Nullable String reason) {
        if (obj == null) {
            throw new IllegalArgumentException(reason);
        } return obj;
    }

    //--endregion Object----------------------------------------------------------------------------
    //--region Array--------------------------------------------------------------------------------

    public static @NonNull <T> T[] nonNullArr(
            @Nullable T[] arr, boolean canBeEmpty
    ) {
        return nonNullArr(arr, canBeEmpty, null);
    }

    public static @NonNull <T> T[] nonNullArr(
            @Nullable T[] arr, boolean canBeEmpty, @Nullable String reason
    ) {
        Check.nonNull(arr, reason);
        if (!canBeEmpty && arr.length == 0) {
            throw new IllegalArgumentException(reason);
        } else {
            for (Object obj : arr) {
                Check.nonNull(obj, reason);
            }
        } return arr;
    }

    //--endregion Array-----------------------------------------------------------------------------
    //--region Collection---------------------------------------------------------------------------

    public static @NonNull <T, C extends Collection<T>> C nonNullCol(
            @Nullable C col, boolean canBeEmpty
    ) {
        return nonNullCol(col, canBeEmpty, null);
    }

    public static @NonNull <T, C extends Collection<T>> C nonNullCol(
            @Nullable C col, boolean canBeEmpty, @Nullable String reason
    ) {
        Check.nonNull(col, reason);
        if (!canBeEmpty && col.size() == 0) {
            throw new IllegalArgumentException(reason);
        } else {
            for (Object obj : col) {
                Check.nonNull(obj, reason);
            }
        } return col;
    }

    //--endregion Collection------------------------------------------------------------------------
    //--region Map----------------------------------------------------------------------------------

    public static @NonNull <K, V, M extends Map<K, V>> M nonNullMap(
            @Nullable M map, boolean canBeEmpty
    ) {
        return nonNullMap(map, canBeEmpty, null);
    }

    public static @NonNull <K, V, M extends Map<K, V>> M nonNullMap(
            @Nullable M map, boolean canBeEmpty, @Nullable String reason
    ) {
        Check.nonNull(map, reason);
        if (!canBeEmpty && map.size() == 0) {
            throw new IllegalArgumentException(reason);
        } else {
            Set<Map.Entry<K, V>> set = map.entrySet();
            for (Map.Entry<K, V> entry : set) {
                Check.nonNull(entry.getKey(), reason);
                Check.nonNull(entry.getValue(), reason);
            }
        } return map;
    }

    //--endregion Map-------------------------------------------------------------------------------
}
