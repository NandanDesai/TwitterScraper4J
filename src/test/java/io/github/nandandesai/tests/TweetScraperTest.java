package io.github.nandandesai.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.nandandesai.TwitterScraper;
import io.github.nandandesai.models.Tweet;

import java.util.List;

public class TweetScraperTest {
    public static void main(String[] args){
        try {
            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            List<Tweet> tweets=twitterScraper.getUserTimeline("fs0c131y");

            for(Tweet tweet:tweets){
                displayTweet(tweet);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void displayTweet(Tweet tweet){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(tweet));
        //System.out.println(profile);
    }
}
