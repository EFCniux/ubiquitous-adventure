package es.niux.efc.core.data.entity;

/**
 * This is a thing which is none. No-thing.
 * <br>Used when an object is required but not needed.
 */
public class NO {
    /**
     * A Nothing.
     * @see NO
     */
    public static final NO THING = new NO();

    private NO() { }
}