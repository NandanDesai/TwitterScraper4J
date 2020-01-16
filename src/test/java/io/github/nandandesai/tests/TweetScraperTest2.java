package io.github.nandandesai.tests;

import io.github.nandandesai.twitterscraper4j.TwitterScraper;

public class TweetScraperTest2 {
    public static void main(String[] args){
        try {
            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            System.out.println(twitterScraper.getTweet("1217827468230434818"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
