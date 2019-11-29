package cz.muni.fi.pb162.hw01;

/**
 * @author Jakub Cechacek
 *
 * Basic set of protocols
 */
enum Protocol {

    FTP(21),
    HTTP(80),
    HTTPS(443),
    SFTP(22),
    SSH(22);


    private final int port;
    private final String schema;

    Protocol(int port) {
        this.schema = this.name().toLowerCase();
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getSchema() {
        return schema;
    }
}
