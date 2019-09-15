package io.github.nandandesai.tests;

import io.github.nandandesai.TwitterScraper;
import io.github.nandandesai.models.Tweet;

import java.util.Iterator;
import java.util.List;

public class KeywordSearchPageIteratorTest {
    public static void main(String[] args){
        test("Kashmir");
    }
//
    private static void test(String keyword){
        int tweetCount=0;
        try {

            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            Iterator<List<Tweet>> it=twitterScraper.searchAllTweetsWithKeyword(keyword);
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
