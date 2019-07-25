package io.github.nandandesai;

import io.github.nandandesai.models.Tweet;
import io.github.nandandesai.models.UserSearchResult;
import io.github.nandandesai.models.Profile;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.pmw.tinylog.Logger;

import java.io.IOException;
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
        response= Jsoup.connect("https://mobile.twitter.com").headers(Utils.getHttpHeaders()).ignoreHttpErrors(true).followRedirects(true).method(Connection.Method.GET).execute();
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
        TweetScraper tweetScraper=new TweetScraper(cookies);
        return tweetScraper.getHomeTimeline(username);
    }

    //the series of tweets that appear above the present tweet in the thread.
    //This means that even if there are tweets below this tweet in the same thread, they are not considered here.0
    public List<Tweet> getUpperThread(String username, String tweetId) throws IOException {
        TweetScraper tweetScraper=new TweetScraper(cookies);
        return tweetScraper.getUpperThread(username,tweetId);
    }
}
