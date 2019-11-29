package cz.muni.fi.pb162.hw01.url;

/**
 * Implementation of this interface can be used to resolve port number
 *
 * @author Jakub Cechacek
 */
public interface PortResolver {

    /**
     * Resolves default port for protocol based on schema
     * @param schema protocol schema
     * @return default port number
     */
    int getPort(String schema);
}
