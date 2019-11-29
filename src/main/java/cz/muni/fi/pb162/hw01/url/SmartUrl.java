package cz.muni.fi.pb162.hw01.url;

/**
 * Representation of URL
 *
 * @author Jakub Cechacek
 */
public interface SmartUrl extends SmartDecomposable, SmartComparable {
    /**
     * Returns canonical representation of URL.
     *
     * A canonical URL fulfills the following criteria
     * <ul>
     *  <li>Port is omitted if it is the default port for given protocol</li>
     *  <li>No trailing slashes</li>
     *  <li>Relative path segments referring to current or parent directory are resolved</li>
     *  <li>Order of query parameters is fixed</li>
     * </ul>
     *
     * @return String representation of this URL in it's canonical form or null if URL is invalid
     */
    String getAsString();

    /**
     * @return String representation of this URL as originally initialized
     */
    String getAsRawString();
}
