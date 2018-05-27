package es.niux.efc.core.presentation.view.text;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * An empty {@link TextWatcher} to reduce boilerplate code.
 */
public interface TextChangedListener extends TextWatcher {
    @Override
    default void beforeTextChanged(@NonNull CharSequence s, int start, int count, int after) { }

    @Override
    default void onTextChanged(@NonNull CharSequence s, int start, int before, int count) { }

    @Override
    default void afterTextChanged(@NonNull Editable s) { }
}
