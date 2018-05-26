package es.niux.efc.core.common.util;

import android.support.annotation.NonNull;

import java.util.regex.Pattern;

/**
 * A collection of regular expression patterns
 */
@SuppressWarnings("unused")
public final class Patterns {
    private Patterns() { throw new UnsupportedOperationException("Nope."); }

    public static final @NonNull Pattern NOT_EMPTY = Pattern.compile("(?m)^\\s*\\S+[\\s\\S]*$");
    public static final @NonNull Pattern EMAIL = android.util.Patterns.EMAIL_ADDRESS;
    public static final @NonNull Pattern PHONE = Pattern.compile("(^\\+\\d+)?[0-9\\s()-]*");
}
