package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.DefaultPortResolver;
import cz.muni.fi.pb162.hw01.url.SmartUrl;
import cz.muni.fi.pb162.hw01.url.SmartComparable;
import cz.muni.fi.pb162.hw01.url.SmartDecomposable;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Marek Hurban
 */
public class Url implements SmartUrl, SmartDecomposable, SmartComparable {
    private String rawUrl;

    Url(String s) {
        this.rawUrl = s;
    }
    /**
     *
     * @param schema url scheme
     * @return true is protocol is missing or lacks "://"
     */
    private boolean missingProt(String schema) {
        try {
            Pattern pattern = Pattern.compile("[a-zA-Z].(://)");
            Matcher host = pattern.matcher(schema);
            return !host.find();
        } catch (NullPointerException e) {
            return true;
        }
    }
    /**
     *
     * @param schema url scheme
     * @return true if host is missing
     */
    private boolean invalidHost(String schema) {
        try {
            Pattern pattern = Pattern.compile("\\.[a-zA-Z]");
            Matcher host = pattern.matcher(schema);
            return !host.find();
        } catch (NullPointerException e) {
            return true;
        }
    }
    /**
     *
     * @param schema url scheme
     * @return port as string
     */
    private String findPort(String schema) {
        Pattern pattern = Pattern.compile(":+[0-9]+");
        Matcher port = pattern.matcher(schema);
        if (port.find()) {
            return port.group(0).substring(1);
        }
        return "0";
    }

    @Override
    public String getAsString() {
        if (missingProt(this.rawUrl) || invalidHost(this.rawUrl)) {
            return null;
        }
        String cannUrl = getProtocol() + "://" + getHost();
        String actualPort = findPort(this.rawUrl);
        if (Integer.parseInt(actualPort) != getPort() && !actualPort.equals("0")) {
            cannUrl += ":" + actualPort;
        }
        String path = getPath();
        if (path.length() > 0) {
            cannUrl += "/" + path;
        }
        String query = getQuery();
        if ( query.length() > 0) {
            cannUrl += "?" + query;
        }
        String fragment = getFragment();
        if (fragment.length() > 0) {
            cannUrl += "#" + getFragment();
        }
        return cannUrl;
    }

    @Override
    public String getAsRawString() {
        return this.rawUrl;
    }


    @Override
    public String getHost() {
        Pattern pattern = Pattern.compile("[a-zA-Z+.]+(?=[:/])");
        Matcher host = pattern.matcher(this.rawUrl);
        List<String> matches = new ArrayList<>();
        while(host.find()) {
            matches.add(host.group(0));
        }
        return matches.get(1);
    }

    @Override
    public String getProtocol() {
        return this.rawUrl.split("://")[0];
    }

    @Override
    public int getPort() {
        DefaultPortResolver portResolver = new DefaultPortResolver();
        return portResolver.getPort(this.getProtocol().toUpperCase());
    }
    /**
     *
     * @param pathStrings path with unsolved ralative path segments
     * @return path with resolved relative segments.
     */
    private String resRelativeSegs(String[] pathStrings) {
        List<String> cleanPath = new ArrayList<>();
        for (String pathString : pathStrings) {
            if (pathString.equals("..")) {
                try {
                    cleanPath.remove(cleanPath.size() - 1);
                } catch (IndexOutOfBoundsException e) {
                    return "";
                }
            } else if (!pathString.equals(".")) {
                cleanPath.add(pathString);
            }
        }
        String[] strResult = cleanPath.toArray(new String[0]);
        return String.join("/", strResult);
    }

    @Override
    public String getPath() {
        Pattern pattern = Pattern.compile("[^:/]/.*[/?]");
        Matcher host = pattern.matcher(this.rawUrl);
        if(host.find()) {
            String str1 = host.group(0);
            str1 = str1.substring(2, str1.length() - 1);
            String[] strings = str1.split("/");
            return resRelativeSegs(strings);
        }
        String[] spRaw = this.rawUrl.split("/");
        try {
            return spRaw[3];
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    @Override
    public String getQuery() {
        String[] str = this.rawUrl.split("\\?");
        if (str.length == 1) {
            return "";
        }
        String bigQuery = str[str.length - 1];
        String query = bigQuery.split("#")[0];
        String[] strings = query.split("&");
        Arrays.sort(strings);
        return String.join("&", strings);
    }

    @Override
    public String getFragment() {
        try {
            return this.rawUrl.split("#")[1];
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    @Override
    public boolean isSameAs(SmartUrl url) {
        return url.getAsString().equals(this.getAsString());
    }

    @Override
    public boolean isSameAs(String url) {
        Url comprUrl = new Url(url);
        return comprUrl.getAsString().equals(this.getAsString());
    }
}
