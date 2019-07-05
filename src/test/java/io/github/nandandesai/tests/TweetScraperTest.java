package io.github.nandandesai.tests;

import io.github.nandandesai.TwitterScraper;

public class TweetScraperTest {
    public static void main(String[] args){
        try {
            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            twitterScraper.getUserTimeline("fs0c131y");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
