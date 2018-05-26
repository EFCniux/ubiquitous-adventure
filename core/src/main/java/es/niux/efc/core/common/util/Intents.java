package es.niux.efc.core.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;

/**
 * A utility class to create {@link Intent Intents}
 */
@SuppressWarnings("unused")
public final class Intents {
    private Intents() { throw new UnsupportedOperationException("Nope."); }

    //--region View---------------------------------------------------------------------------------

    public static @NonNull Intent viewUri(
            @NonNull Uri uri
    ) {
        return new Intent(Intent.ACTION_VIEW, Check.nonNull(uri));
    }

    //--endregion View------------------------------------------------------------------------------
    //--region Phone--------------------------------------------------------------------------------

    public enum PhoneIntentType {
        TELEPHONE("tel"),
        VOICEMAIL("voicemail");

        public final @NonNull String uriName;
        PhoneIntentType(@NonNull String uriName) {
            Check.nonNull(uriName);
            this.uriName = uriName;
        }
    }

    public static @NonNull Intent phoneDial(
            @NonNull PhoneIntentType type,
            @NonNull String phoneNumber
    ) {
        return new Intent(Intent.ACTION_DIAL, Uri.parse(
                Check.nonNull(type).uriName + ":" + Check.nonNull(phoneNumber)
        ));
    }

    @RequiresPermission(android.Manifest.permission.CALL_PHONE)
    public static @NonNull Intent phoneCall(
            @NonNull PhoneIntentType type,
            @NonNull String phoneNumber
    ) {
        return new Intent(Intent.ACTION_CALL, Uri.parse(
                Check.nonNull(type).uriName + ":" + Check.nonNull(phoneNumber)
        ));
    }

    //--endregion Phone-----------------------------------------------------------------------------
    //--region Email--------------------------------------------------------------------------------

    public static @NonNull Intent emailSend(
            @NonNull String[] to,
            @NonNull String[] cc,
            @NonNull String[] bcc,
            @NonNull String subject,
            @NonNull String body
    ) {
        Check.nonNullArr(to, true);
        Check.nonNullArr(cc, true);
        Check.nonNullArr(bcc, true);
        Check.nonNull(subject);
        Check.nonNull(body);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));

        if (to.length != 0) { intent.putExtra(Intent.EXTRA_EMAIL, to); }
        if (cc.length != 0) { intent.putExtra(Intent.EXTRA_CC, cc); }
        if (bcc.length != 0) { intent.putExtra(Intent.EXTRA_BCC, bcc); }
        if (!subject.isEmpty()) { intent.putExtra(Intent.EXTRA_SUBJECT, subject); }
        if (!body.isEmpty()) { intent.putExtra(Intent.EXTRA_TEXT, body); }

        return intent;
    }

    //--endregion Email-----------------------------------------------------------------------------
    //--region File---------------------------------------------------------------------------------

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static @NonNull Intent fileOpen(
            @Nullable String[] mineTypes, boolean canMultiple, boolean isLocalOnly
    ) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (mineTypes == null) {
            intent.setType("*/*");
        } else if (mineTypes.length == 1) {
            intent.setType(Check.nonNull(mineTypes[0]));
        } else {
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, Check.nonNullArr(mineTypes, false));
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, isLocalOnly);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, canMultiple);
        return intent;
    }

    public static @NonNull Intent fileGet(
            @Nullable String mineType, boolean canMultiple, boolean isLocalOnly
    ) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(mineType != null ? mineType : "*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, isLocalOnly);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, canMultiple);
        }
        return intent;
    }

    //--endregion File------------------------------------------------------------------------------

    public static boolean canResolve(
            @NonNull Context context,
            @NonNull Intent activityIntent
    ) {
        return Check.nonNull(activityIntent)
                .resolveActivity(Check.nonNull(context).getPackageManager()) != null;
    }

    public static @NonNull Intent createChooser(
            @NonNull Intent intent, @Nullable CharSequence title
    ) {
        return Intent.createChooser(Check.nonNull(intent), title);
    }
}
