package cz.muni.fi.pb162.hw01.url;

/**
 * Capability to compare URL
 *
 * @author Jakub Cechacek
 */
public interface SmartComparable {
    /**
     * Compares two URLs for equality. URLs are equal if their canonical form is the same.
     *
     * @param url another URL
     * @return true if this and the other URL are equal, false otherwise
     */
    boolean isSameAs(SmartUrl url);

    /**
     * @see #isSameAs(String)
     * @param url another URL
     * @return true if this and the other URL are equal, false otherwise
     */
    boolean isSameAs(String url);
}
