package es.niux.efc.core.presentation.navigator;

public interface INavigator {
    /**
     * Called when user desires to navigate up.
     *
     * @return boolean Return true to allow default up navigation to proceed, or false otherwise
     * @see <a href="https://developer.android.com/training/implementing-navigation/ancestral.html">Providing up navigation</a>
     */
    default boolean onNavigateUp()  {
        return true;
    }

    /**
     * Called when user desires to navigate back.
     *
     * @return boolean Return false to allow default back navigation to proceed, or false otherwise
     * @see <a href="https://developer.android.com/training/implementing-navigation/temporal.html">Providing back navigation</a>
     */
    default boolean onNavigateBack() {
        return true;
    }
}
