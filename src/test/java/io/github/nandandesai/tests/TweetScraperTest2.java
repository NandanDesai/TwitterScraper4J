package io.github.nandandesai.tests;

import io.github.nandandesai.TwitterScraper;
import io.github.nandandesai.models.Tweet;

import java.util.List;

public class TweetScraperTest2 {
    public static void main(String[] args){
        try {
            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            System.out.println(twitterScraper.getTweet("1158474004103147522"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
