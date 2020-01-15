package io.github.nandandesai.tests;

import io.github.nandandesai.twitterscraper4j.TwitterScraper;
import io.github.nandandesai.twitterscraper4j.models.Tweet;

import java.util.List;

public class HashtagSearchTest {
    public static void main(String[] args){
        testHashtagSearch("#Kashmir");
    }

    private static void testHashtagSearch(String hashtag){
        try {
            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            List<Tweet> tweets=twitterScraper.searchTweetsWithHashtag(hashtag);

            for(Tweet tweet:tweets){
                System.out.println(tweet);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
