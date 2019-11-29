package cz.muni.fi.pb162.hw01;

import cz.muni.fi.pb162.hw01.url.PortResolver;

/**
 * @author Jakub Cechacek
 *
 * Resolves port numbers based on {@link Protocol} enum
 */
public class DefaultPortResolver implements PortResolver {

    /**
     * Resolves default port for protocol based on schema
     * @param schema protocol schema
     * @throws  IllegalArgumentException if given schema is unknown to this resolver.
     * @return default port number
     */
    @Override
    public int getPort(String schema) {
        Protocol protocol =  Protocol.valueOf(schema.toUpperCase());
        return protocol.getPort();
    }
}
