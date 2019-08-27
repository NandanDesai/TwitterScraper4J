package io.github.nandandesai.tests;

import io.github.nandandesai.TwitterScraper;
import io.github.nandandesai.models.Tweet;

import java.util.List;

public class TweetScraperTest {
    public static void main(String[] args){
        try {
            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            List<Tweet> tweets=twitterScraper.getUserTimeline("fs0c131y");

            for(Tweet tweet:tweets){
                System.out.println(tweet);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
