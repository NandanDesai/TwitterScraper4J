package io.github.nandandesai;

import io.github.nandandesai.models.Tweet;
import io.github.nandandesai.models.UserSearchResult;
import io.github.nandandesai.models.Profile;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class which does all the heavy duty work of scraping Twitter page
 * @author Nandan Desai
 */
public class TwitterScraper {
    private static TwitterScraper scraper;
    private Map<String, String> cookies;
    private Connection.Response response;

    private TwitterScraper() throws IOException {
        Logger.info("Initializing Twitter Scraper!");
        response= Jsoup.connect("https://mobile.twitter.com").headers(getHttpHeaders()).ignoreHttpErrors(true).followRedirects(true).method(Connection.Method.GET).execute();
        cookies = response.cookies();
        Logger.info("Cookies: "+cookies.toString());
    }

    /**
     * Returns an instance of the TwitterScraper class
     *
     * @return object of TwitterScraper class.
     * @throws IOException
     */
    public static TwitterScraper getInstance() throws IOException {
        synchronized (TwitterScraper.class) {
            if (scraper == null) {
                scraper=new TwitterScraper();
            }
            return scraper;
        }
    }

    /**
     * Returns the profile details of the given username.
     *
     * @param username Twitter username, for example, realDonaldTrump
     * @return Profile is returned if the profile exists. Else, null is returned. Be careful to check for null otherwise you'll get hit with NullPointerException
     * @throws IOException
     */

    public Profile getProfile(String username) throws IOException {
        return new ProfileScraper().getProfile(username, cookies);
    }

    public List<UserSearchResult> searchUser(String query) throws IOException {
        SearchScraper searchScraper= new SearchScraper();
        return searchScraper.searchUser(query, cookies);
    }

    public List<Tweet> getUserTimeline(String username) throws IOException {
        TweetScraper tweetScraper=new TweetScraper();
        return tweetScraper.getHomeTimeline(username, cookies);
    }

    //returns HTTP headers
    static Map<String, String> getHttpHeaders(){
        Logger.info("Using Mozilla headers. Trying to emulate a browser as much as possible.");
        Map<String, String> headers=new HashMap<>();
        headers.put("User-Agent","Mozilla/5.0 (X11; Linux x86_64; rv:58.0) Gecko/20100101 Firefox/58.0");
        headers.put("Accept-Language","en-US,en;q=0.5");
        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.put("Accept-Charset","utf-8");
        headers.put("Connection","keep-alive");
        headers.put("Upgrade-Insecure-Requests","1");
        return headers;
    }


}
