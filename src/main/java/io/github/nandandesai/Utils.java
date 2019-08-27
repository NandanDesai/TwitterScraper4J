package io.github.nandandesai;

import io.github.nandandesai.exceptions.TwitterException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class Utils {

    //returns HTTP headers
    static Map<String, String> getHttpHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:58.0) Gecko/20100101 Firefox/58.0");
        headers.put("Accept-Language", "en-US,en;q=0.5");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.put("Accept-Charset", "utf-8");
        headers.put("Connection", "keep-alive");
        headers.put("Upgrade-Insecure-Requests", "1");
        return headers;
    }

    static Document getDocument(String url, Map cookies, Class callingClass) throws IOException, TwitterException {
        Connection.Response response = Jsoup.connect(url).headers(getHttpHeaders()).ignoreHttpErrors(true).followRedirects(true)
                .method(Connection.Method.POST)
                .header("Referer", "https://mobile.twitter.com/")
                .cookies(cookies)
                .execute();
        int statusCode = response.statusCode();
        if (statusCode == 404 && callingClass.equals(ProfileScraper.class)) {
            throw new TwitterException(404, "User not found.");
        } else if (statusCode == 404 && callingClass.equals(TweetScraper.class)) {
            throw new TwitterException(404, "No tweet(s) found.");
        } else if (statusCode != 200) {
            throw new TwitterException(statusCode, response.statusMessage());
        }
        return response.parse();
    }

}
