package io.github.nandandesai.tests;

import io.github.nandandesai.TwitterScraper;

public class TweetScraperTest2 {
    public static void main(String[] args){
        try {
            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            System.out.println(twitterScraper.getTweet("1182159712998973440"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
