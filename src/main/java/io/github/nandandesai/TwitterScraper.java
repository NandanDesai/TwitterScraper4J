package io.github.nandandesai;

import io.github.nandandesai.exceptions.TwitterException;
import io.github.nandandesai.models.Tweet;
import io.github.nandandesai.models.User;
import io.github.nandandesai.models.Profile;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
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
        response= Jsoup.connect("https://mobile.twitter.com").headers(Utils.getHttpHeaders()).ignoreHttpErrors(true).followRedirects(true).method(Connection.Method.GET).execute();
        cookies = response.cookies();
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

    public Profile getProfile(String username) throws IOException, TwitterException {
        if (username == null || username.equals("")) {
            throw new IllegalArgumentException("\"username\" cannot be null or empty");
        }
        return new ProfileScraper().getProfile(username, cookies);
    }

    public List<User> searchUser(String query) throws IOException, TwitterException {
        if (query == null || query.equals("")) {
            throw new IllegalArgumentException("\"query\" cannot be null or empty");
        }
        SearchScraper searchScraper= new SearchScraper();
        return searchScraper.searchUser(query, cookies);
    }

    public List<Tweet> searchTweetsWithHashtag(String hashtag) throws IOException, TwitterException {
        if (hashtag == null || hashtag.equals("")) {
            throw new IllegalArgumentException("\"hashtag\" cannot be null or empty");
        }
        SearchScraper searchScraper= new SearchScraper();
        return searchScraper.searchHashtag(hashtag, cookies);
    }

    public List<Tweet> searchTweetsWithKeyword(String keyword) throws IOException, TwitterException {
        if (keyword == null || keyword.equals("")) {
            throw new IllegalArgumentException("\"keyword\" cannot be null or empty");
        }
        SearchScraper searchScraper= new SearchScraper();
        return searchScraper.searchKeyword(keyword, cookies);
    }

    public List<Tweet> getUserTimeline(String username) throws IOException, TwitterException {
        if (username == null || username.equals("")) {
            throw new IllegalArgumentException("\"username\" cannot be null or empty");
        }
        TweetScraper tweetScraper=new TweetScraper(cookies);
        return tweetScraper.getHomeTimeline(username);
    }

    //the series of tweets that appear above the present tweet in the thread.
    //This means that even if there are tweets below this tweet in the same thread, they are not considered here.0
    public List<Tweet> getUpperThread(String tweetId) throws IOException, TwitterException {
        if (tweetId == null || tweetId.equals("")) {
            throw new IllegalArgumentException("\"tweetId\" cannot be null or empty");
        }
        TweetScraper tweetScraper=new TweetScraper(cookies);
        return tweetScraper.getUpperThread(tweetId);
    }

    public List<String> getWorldwideTrends() throws IOException, TwitterException {
        SearchScraper searchScraper=new SearchScraper();
        return searchScraper.worldwideTrends(cookies);
    }

    public Tweet getTweet(String tweetId) throws IOException, TwitterException {
        if (tweetId == null || tweetId.equals("")) {
            throw new IllegalArgumentException("\"tweetId\" cannot be null or empty");
        }
        TweetScraper tweetScraper=new TweetScraper(cookies);
        return tweetScraper.getTweet(tweetId);
    }

    public Iterator<List<Tweet>> getAllTweets(String username) {
        if (username == null || username.equals("")) {
            throw new IllegalArgumentException("\"username\" cannot be null or empty");
        }
        return new ProfilePageIterator(username, cookies);
    }

    public Iterator<List<User>> getAllFriends(String username){
        if (username == null || username.equals("")) {
            throw new IllegalArgumentException("\"username\" cannot be null or empty");
        }
        String url="https://mobile.twitter.com/i/nojs_router?path=/"+username+"/following";
        return new UserListIterator(url, cookies);
    }

    public Iterator<List<User>> getAllFollowers(String username){
        if (username == null || username.equals("")) {
            throw new IllegalArgumentException("\"username\" cannot be null or empty");
        }
        String url="https://mobile.twitter.com/i/nojs_router?path=/"+username+"/followers";
        return new UserListIterator(url, cookies);
    }

    public Iterator<List<User>> searchAllUsers(String query) {
        if (query == null || query.equals("")) {
            throw new IllegalArgumentException("\"query\" cannot be null or empty");
        }
        query=query.replace(" ","%20");
        String urlPath="/search/users?q="+query+"&s=typd";
        try {
            urlPath= URLEncoder.encode(urlPath, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url="https://mobile.twitter.com/i/nojs_router?path="+urlPath;
        return new UserListIterator(url, cookies);
    }

    public Iterator<List<Tweet>> searchAllTweets(String query){
        if (query == null || query.equals("")) {
            throw new IllegalArgumentException("\"query\" cannot be null or empty");
        }
        query=query.replace(" ","%20");
        String url="";
        try {
            url="https://mobile.twitter.com/i/nojs_router?path="+ URLEncoder.encode("/search?q="+query, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new TweetListIterator(url, cookies);
    }
}
