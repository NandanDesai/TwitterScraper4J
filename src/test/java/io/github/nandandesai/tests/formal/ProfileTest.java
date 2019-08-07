package io.github.nandandesai.tests.formal;

import io.github.nandandesai.TwitterScraper;
import io.github.nandandesai.models.Profile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class ProfileTest {

    User user1API = null;
    User user2API = null;
    User user3API = null;
    User user4API = null;

    Profile user1Scraper = null;
    Profile user2Scraper = null;
    Profile user3Scraper = null;
    Profile user4Scraper = null;


    @Before
    public void initialize() throws IOException, TwitterException, io.github.nandandesai.exceptions.TwitterException, URISyntaxException {
        Credentials credentials=new Credentials();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(credentials.getApiKey())
                .setOAuthConsumerSecret(credentials.getApiSecretKey())
                .setOAuthAccessToken(credentials.getAccessToken())
                .setOAuthAccessTokenSecret(credentials.getAccessTokenSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        TwitterScraper scraper=TwitterScraper.getInstance();

        String testUsername1="fs0c131y";    //non-verified user
        String testUsername2="realDonaldTrump"; //verified user
        String testUsername3="nandankdesai";    //protected user
        String testUsername4="nandankdesai";   //invalid user

        user1API=twitter.showUser(testUsername1);
        user2API=twitter.showUser(testUsername2);
        user3API=twitter.showUser(testUsername3);
        user4API=twitter.showUser(testUsername4);

        user1Scraper = scraper.getProfile(testUsername1);
        user2Scraper = scraper.getProfile(testUsername2);
        user3Scraper = scraper.getProfile(testUsername3);
        user4Scraper = scraper.getProfile(testUsername4);
    }

    @Test
    public void nameTest(){
        Assert.assertEquals(user1API.getName(), user1Scraper.getName());
        Assert.assertEquals(user2API.getName(), user2Scraper.getName());
        Assert.assertEquals(user3API.getName(), user3Scraper.getName());
        Assert.assertEquals(user4API.getName(), user4Scraper.getName());
    }

    @Test
    public void descriptionTest(){
        Assert.assertEquals(user1API.getDescription(), user1Scraper.getDescription());
        Assert.assertEquals(user2API.getDescription(), user2Scraper.getDescription());
        Assert.assertEquals(user3API.getDescription(), user3Scraper.getDescription());
        Assert.assertEquals(user4API.getDescription(), user4Scraper.getDescription());
    }

    @Test
    public void locationTest(){
        Assert.assertEquals(user1API.getLocation(), user1Scraper.getLocation());
        Assert.assertEquals(user2API.getLocation(), user2Scraper.getLocation());
        Assert.assertEquals(user3API.getLocation(), user3Scraper.getLocation());
        Assert.assertEquals(user4API.getLocation(), user4Scraper.getLocation());
    }

    @Test
    public void followersCountTest(){
        Assert.assertEquals(user1API.getFollowersCount(), user1Scraper.getNoOfFollowers());
        Assert.assertEquals(user2API.getFollowersCount(), user2Scraper.getNoOfFollowers());
        Assert.assertEquals(user3API.getFollowersCount(), user3Scraper.getNoOfFollowers());
        Assert.assertEquals(user4API.getFollowersCount(), user4Scraper.getNoOfFollowers());
    }

    @Test
    public void friendsCountTest(){
        Assert.assertEquals(user1API.getFriendsCount(), user1Scraper.getNoOfFriends());
        Assert.assertEquals(user2API.getFriendsCount(), user2Scraper.getNoOfFriends());
        Assert.assertEquals(user3API.getFriendsCount(), user3Scraper.getNoOfFriends());
        Assert.assertEquals(user4API.getFriendsCount(), user4Scraper.getNoOfFriends());
    }

    @Test
    public void tweetsCountTest(){
        Assert.assertEquals(user1API.getStatusesCount(), user1Scraper.getNoOfTweets());
        Assert.assertEquals(user2API.getStatusesCount(), user2Scraper.getNoOfTweets());
        Assert.assertEquals(user3API.getStatusesCount(), user3Scraper.getNoOfTweets());
        Assert.assertEquals(user4API.getStatusesCount(), user4Scraper.getNoOfTweets());
    }

    @Test
    public void verifiedTest(){
        Assert.assertEquals(user1API.isVerified(), user1Scraper.isVerified());
        Assert.assertEquals(user2API.isVerified(), user2Scraper.isVerified());
        Assert.assertEquals(user3API.isVerified(), user3Scraper.isVerified());
        Assert.assertEquals(user4API.isVerified(), user4Scraper.isVerified());
    }

    @Test
    public void protectedTest(){
        Assert.assertEquals(user1API.isProtected(), user1Scraper.isProtected());
        Assert.assertEquals(user2API.isProtected(), user2Scraper.isProtected());
        Assert.assertEquals(user3API.isProtected(), user3Scraper.isProtected());
        Assert.assertEquals(user4API.isProtected(), user4Scraper.isProtected());
    }

    @Test
    public void userURLTest(){
        Assert.assertEquals(user1API.getURL(), user1Scraper.getUserWebsite().toString());
        Assert.assertEquals(user2API.getURL(), user2Scraper.getUserWebsite().toString());
        Assert.assertEquals(user3API.getURL(), user3Scraper.getUserWebsite().toString());
        Assert.assertEquals(user4API.getURL(), user4Scraper.getUserWebsite().toString());
    }
}
