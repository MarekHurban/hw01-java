package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.url.SmartUrl;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

/**
 * @author Jakub Cechacek
 */

public class SmartUrlTest {

    @Test
    public void shouldBeEqualUrls() {
        SoftAssertions softly = new SoftAssertions();
        SmartUrl expected = new Url("http://domain.com/articles/technology/25-moon-landing?order=asc&read=true");
        assertEqualUrl(softly,"http://domain.com/articles/technology/25-moon-landing?order=asc&read=true" , expected);
        assertEqualUrl(softly,"http://domain.com/articles/technology/25-moon-landing/?order=asc&read=true" , expected);
        assertEqualUrl(softly,"http://domain.com/articles/technology/25-moon-landing?read=true&order=asc" , expected);
        assertEqualUrl(softly,"http://domain.com/articles/technology/25-moon-landing?order=asc&read=true" , expected);
        assertEqualUrl(softly,"http://domain.com:80/articles/technology/25-moon-landing?order=asc&read=true" , expected);
        assertEqualUrl(softly,"http://domain.com/articles/technology/./25-moon-landing?order=asc&read=true" , expected);
        assertEqualUrl(softly,"http://domain.com/articles/technology/public/../25-moon-landing?order=asc&read=true" , expected);
        softly.assertAll();
    }

    @Test
    public void shouldNotBeEqualUrls() {
        SoftAssertions softly = new SoftAssertions();
        SmartUrl expected = new Url("http://domain.com/articles/technology/25-moon-landing?order=asc&read=true");
        assertNotEqualUrl(softly,"http://domain.com/articles/technology/25-moon-landing?order=asc&read=true&pg=2" , expected);
        assertNotEqualUrl(softly, "http://domain.com/articles/technology/25-moon-landing?order=asc&read=true#foo" , expected);
        assertNotEqualUrl(softly,"http://domain.com/articles/25-moon-landing?order=asc&read=true" , expected);
        assertNotEqualUrl(softly,"http://domain.com:8080/articles/technology/25-moon-landing?order=asc&read=true" , expected);
        softly.assertAll();
    }

    @Test
    public void shouldStripTrailingSlash() {
        SoftAssertions softly = new SoftAssertions();
        assertNoTrailingSlash(softly, "http://domain.com/", "http://domain.com");
        assertNoTrailingSlash(softly, "http://domain.com/articles/", "http://domain.com/articles");
        assertNoTrailingSlash(softly, "http://domain.com/articles", "http://domain.com/articles");
        assertNoTrailingSlash(softly, "http://domain.com/articles?order=desc", "http://domain.com/articles?order=desc");
        softly.assertAll();
    }

    @Test
    public void shouldNotHavePath() {
        SoftAssertions softly = new SoftAssertions();
        assertPath(softly, "http://domain.com");
        assertPath(softly, "http://domain.com/");
        assertPath(softly, "http://domain.com?");
        assertPath(softly, "http://domain.com/articles/../");
        assertPath(softly, "http://domain.com/articles/../..");
        assertPath(softly, "http://domain.com?order=asc");
        softly.assertAll();
    }


    @Test
    public void shouldHavePath() {
        SoftAssertions softly = new SoftAssertions();
        assertPath(softly, "http://domain.com/articles", "articles");
        assertPath(softly, "http://domain.com/articles/", "articles");
        assertPath(softly, "http://domain.com/articles/.", "articles");
        assertPath(softly,"http://domain.com/articles/technology?order=asc&read=true&pg=2" , "articles/technology");
        softly.assertAll();
    }

    @Test
    public void shouldNotHaveQuery() {
        SoftAssertions softly = new SoftAssertions();
        assertEmptyQuery(softly, "http://domain.com");
        assertEmptyQuery(softly, "http://domain.com?");
        assertEmptyQuery(softly, "http://domain.com/articles/");
        assertEmptyQuery(softly, "http://domain.com/articles?");
        softly.assertAll();
    }

    @Test
    public void shouldHaveNormalizedQuery() {
        SoftAssertions softly = new SoftAssertions();
        SmartUrl u = new Url("http://domain.com/articles?foo=bar&buz=qux&c&order=desc");
        assertQuery(softly, "http://domain.com/articles?c&foo=bar&order=desc&buz=qux", u.getQuery());
        assertQuery(softly, "http://domain.com/articles?buz=qux&c&foo=bar&order=desc", u.getQuery());
        softly.assertAll();
    }

    @Test
    public void shouldHaveQuery() {
        SoftAssertions softly = new SoftAssertions();
        SmartUrl u = new Url("http://domain.com/articles?foo=bar&buz=qux&c&order=desc");
        softly.assertThat(u.getQuery()).isNotNull();
        softly.assertThat(u.getQuery()).isNotBlank();
        softly.assertThat(u.getQuery()).contains("foo=bar");
        softly.assertThat(u.getQuery()).contains("buz=qux");
        softly.assertThat(u.getQuery()).contains("order=desc");
        softly.assertAll();
    }

    @Test
    public void shouldHaveFragment() {
        SoftAssertions softly = new SoftAssertions();
        assertFragment(softly, "http://domain.com#title", "title");
        assertFragment(softly, "http://domain.com/#title", "title");
        assertFragment(softly, "http://domain.com/articles#title", "title");
        assertFragment(softly, "http://domain.com/articles?foo=bar#title", "title");
        softly.assertAll();
    }

    @Test
    public void shouldNotHaveFragment() {
        SoftAssertions softly = new SoftAssertions();
        assertEmptyFragment(softly, "http://domain.com");
        assertEmptyFragment(softly, "https://domain.com/articles");
        assertEmptyFragment(softly, "http://domain.com/articles?foo=bar");
        softly.assertAll();
    }

    @Test
    public void shouldHaveProtocol() {
        SoftAssertions softly = new SoftAssertions();
        assertProtocol(softly, "http://domain.com/articles", "http");
        assertProtocol(softly, "https://domain.com/articlesh", "https");
        assertProtocol(softly, "ftp://domain.com/files", "ftp");
        assertProtocol(softly, "sftp://domain.com/files", "sftp");
        assertProtocol(softly, "ssh://myhost.local", "ssh");
        softly.assertAll();
    }

    private void assertProtocol(SoftAssertions assertions, String url, String expectedProtocol) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getProtocol()).isEqualTo(expectedProtocol);
    }

    private void assertFragment(SoftAssertions assertions, String url, String expectedFragment) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getFragment()).isEqualTo(expectedFragment);
    }

    private void assertEmptyFragment(SoftAssertions assertions, String url) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getFragment()).isEmpty();
    }
    
    @Test
    public void shouldHaveDefaultPort() {
        SoftAssertions softly = new SoftAssertions();
        assertDefaultPort(softly, "http://domain.com/articles", 80);
        assertDefaultPort(softly, "https://domain.com/articles", 443);
        assertDefaultPort(softly, "ftp://domain.com/files", 21);
        assertDefaultPort(softly, "sftp://domain.com/files", 22);
        assertDefaultPort(softly, "ssh://myhost.local", 22);
        softly.assertAll();
    }

    private void assertDefaultPort(SoftAssertions assertions, String url, int port) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getPort()).isEqualTo(port);
    }

    private void assertEqualUrl(SoftAssertions assertions, String actual, SmartUrl expected) {
        SmartUrl u = new Url(actual);
        assertions.assertThat(u.isSameAs(expected)).isTrue();
    }

    private void assertNotEqualUrl(SoftAssertions assertions, String actual, SmartUrl expected) {
        SmartUrl u = new Url(actual);
        assertions.assertThat(u.isSameAs(expected)).isFalse();
    }

    private void assertQuery(SoftAssertions assertions, String url, String query) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getQuery()).isEqualTo(query);
    }

    private void assertPath(SoftAssertions assertions, String url) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getPath()).isEmpty();
    }

    private void assertPath(SoftAssertions assertions, String url, String expectedPath) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getPath()).isEqualTo(expectedPath);
    }

    private void assertEmptyQuery(SoftAssertions assertions, String url) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getQuery()).isEmpty();
    }

    private void assertNoTrailingSlash(SoftAssertions assertions, String url, String expected) {
        SmartUrl u = new Url(url);
        assertions.assertThat(u.getAsString()).isEqualTo(expected);
    }
}
