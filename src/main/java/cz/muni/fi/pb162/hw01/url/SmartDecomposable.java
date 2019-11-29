package cz.muni.fi.pb162.hw01.url;

/**
 * Definition of URL components
 *
 * @author Jakub Cechacek
 */
public interface SmartDecomposable {
    /**
     * @return host part of url
     */
    String getHost();

    /**
     * @return protocol part of url
     */
    String getProtocol();

    /**
     * @return port
     */
    int getPort();

    /**
     *  Path part of URL in canonical form. This means no trailing slashes and resolved relative segments
     * @return path part of url
     */
    String getPath();

    /**
     * Query parameters are the part of URL after <code>?</code>
     * Returned the same query string for all equal URLs
     *
     * E.g. "foo=bar&buz=qux" is return for
     *  http://domain.com:80/articles?foo=bar&buz=qux
     *  http://domain.com:80/articles?buz=qux&foo=bar
     *
     * @return query part of url
     */
    String getQuery();

    /**
     * @return fragment part of url
     */
    String getFragment();
}
