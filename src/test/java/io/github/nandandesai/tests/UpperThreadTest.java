package io.github.nandandesai.tests;

import io.github.nandandesai.twitterscraper4j.TwitterScraper;
import io.github.nandandesai.twitterscraper4j.exceptions.TwitterException;
import io.github.nandandesai.twitterscraper4j.models.Tweet;

import java.io.IOException;
import java.util.List;

public class UpperThreadTest {

    public static void main(String[] args){
        try {
            TwitterScraper scraper=TwitterScraper.getInstance();
            //https://mobile.twitter.com/fs0c131y/status/1153053471375056897?p=p
            List<Tweet> tweets=scraper.getUpperThread("1158702409742311424");
            displayTweets(tweets);

            System.out.println();
            System.out.println();
            System.out.println();

            //https://mobile.twitter.com/fs0c131y/status/1153956233533149184?p=p
            tweets=scraper.getUpperThread("1153956233533149184");
            displayTweets(tweets);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public static void displayTweets(List<Tweet> tweets){
        for(Tweet tweet: tweets){
            System.out.println(tweet);
        }
    }
}
