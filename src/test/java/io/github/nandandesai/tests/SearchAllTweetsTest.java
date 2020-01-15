package io.github.nandandesai.tests;

import io.github.nandandesai.twitterscraper4j.TwitterScraper;
import io.github.nandandesai.twitterscraper4j.models.Tweet;

import java.util.Iterator;
import java.util.List;

public class SearchAllTweetsTest {
    public static void main(String[] args){
        test("hindi imposition");
    }

    static void test(String query){
        int tweetCount=0;
        try {

            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            Iterator<List<Tweet>> it=twitterScraper.searchAllTweets(query);
            while(it.hasNext()){
                List<Tweet> tweets=it.next();
                for(Tweet tweet:tweets){
                    System.out.println(tweet);
                    tweetCount++;
                }
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("No of tweets displayed: "+tweetCount);
    }
}
