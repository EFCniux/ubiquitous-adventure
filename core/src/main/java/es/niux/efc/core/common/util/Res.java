package es.niux.efc.core.common.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.AnyRes;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.XmlRes;
import android.support.v4.content.ContextCompat;

import java.util.Locale;

/**
 * A utility class to access application resources
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class Res {
    private Res() { throw new UnsupportedOperationException("Nope."); }

    //--region Int----------------------------------------------------------------------------------

    public static int getInt(
            @NonNull Context context, @IntegerRes int resId
    ) {
        return Check.nonNull(context).getResources().getInteger(resId);
    }

    public static int[] getIntArr(
            @NonNull Context context, @ArrayRes int resId
    ) {
        return Check.nonNull(context).getResources().getIntArray(resId);
    }

    //--endregion Int--------------------------------------------------------------------------------
    //--region Dimen--------------------------------------------------------------------------------

    public static float getDimen(
            @NonNull Context context, @DimenRes int resId
    ) {
        return Check.nonNull(context).getResources().getDimension(resId);
    }

    //--endregion Dimen-----------------------------------------------------------------------------
    //--region String-------------------------------------------------------------------------------

    public static @NonNull String getString(
            @NonNull Context context, @StringRes int resId
    ) {
        return Check.nonNull(context).getResources().getString(resId);
    }

    public static @NonNull String getString(
            @NonNull Context context, @StringRes int resId, @NonNull Object... formatArgs
    ) {
        return Check.nonNull(context).getResources().getString(resId, Check.nonNullArr(formatArgs, false));
    }

    public static @NonNull String[] getStringArr(
            @NonNull Context context, @ArrayRes int resId
    ) {
        return Check.nonNull(context).getResources().getStringArray(resId);
    }

    //--endregion String----------------------------------------------------------------------------
    //--region Color--------------------------------------------------------------------------------

    public static @ColorInt int getColor(
            @NonNull Context context, @ColorRes int resId
    ) {
        return ContextCompat.getColor(Check.nonNull(context), resId);
    }

    public static @ColorInt int getColorAttr(
            @NonNull Context context, @AttrRes int resId
    ) {
        TypedArray typedArray = Check.nonNull(context).obtainStyledAttributes(new int[]{resId});
        int color = typedArray.getColor(0, 0);
        typedArray.recycle();

        if (color == 0) {
            throw new Resources.NotFoundException("Resource '" + String.valueOf(resId) + "' not found!");
        } return color;
    }

    public static @NonNull ColorStateList getColorStateList(
            @NonNull Context context, @ColorRes int resId
    ) {
        ColorStateList colorStateList = ContextCompat.getColorStateList(Check.nonNull(context), resId);
        if (colorStateList == null) {
            throw new Resources.NotFoundException("Resource '" + String.valueOf(resId) + "' not found!");
        } return colorStateList;
    }

    //--endregion Color-----------------------------------------------------------------------------
    //--region Drawable-----------------------------------------------------------------------------

    public static @NonNull Drawable getDrawable(
            @NonNull Context context, @DrawableRes int resId
    ) {
        Drawable drawable = ContextCompat.getDrawable(Check.nonNull(context), resId);
        if (drawable == null) {
            throw new Resources.NotFoundException("Resource '" + String.valueOf(resId) + "' not found!");
        } return drawable;
    }

    //--endregion Drawable--------------------------------------------------------------------------
    //--region Locale-------------------------------------------------------------------------------

    public static @NonNull Locale[] getLocales(
            @NonNull Context context
    ) {
        Locale[] locales;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = Check.nonNull(context).getResources().getConfiguration().getLocales();
            locales = new Locale[localeList.size()];
            for (int i = 0; i < localeList.size(); i += 1) {
                locales[i] = localeList.get(i);
            }
        } else {
            locales = new Locale[]{Check.nonNull(context).getResources().getConfiguration().locale};
        }
        return locales;
    }

    //--endregion Locale----------------------------------------------------------------------------
    //--region Xml----------------------------------------------------------------------------------

    public static @NonNull XmlResourceParser getXml(
            @NonNull Context context, @XmlRes int resId
    ) {
        return Check.nonNull(context).getResources().getXml(resId);
    }

    //--endregion Xml-------------------------------------------------------------------------------
    //--region Raw----------------------------------------------------------------------------------

    public static @NonNull Uri getRaw(
            @NonNull Context context, @AnyRes int resId
    ) {
        Resources resources = Check.nonNull(context).getResources();
        return new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(resId))
                .appendPath(resources.getResourceTypeName(resId))
                .appendPath(resources.getResourceEntryName(resId))
                .build();
    }

    //--endregion Raw-------------------------------------------------------------------------------
}
