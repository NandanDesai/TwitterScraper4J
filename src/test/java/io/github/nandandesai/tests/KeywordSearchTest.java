package io.github.nandandesai.tests;

import io.github.nandandesai.twitterscraper4j.TwitterScraper;
import io.github.nandandesai.twitterscraper4j.models.Tweet;

import java.util.List;

public class KeywordSearchTest {
    public static void main(String[] args){
        testKeywordSearch("PM call");
    }

    private static void testKeywordSearch(String keyword){
        try {
            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            List<Tweet> tweets=twitterScraper.searchTweetsWithKeyword(keyword);

            for(Tweet tweet:tweets){
                System.out.println(tweet);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
