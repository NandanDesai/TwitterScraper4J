package io.github.nandandesai.tests;

import io.github.nandandesai.twitterscraper4j.TweetStream;
import io.github.nandandesai.twitterscraper4j.TweetStreamListener;
import io.github.nandandesai.twitterscraper4j.TwitterScraper;
import io.github.nandandesai.twitterscraper4j.models.Tweet;

import java.io.IOException;
import java.util.List;

public class TweetStreamTest {
    public static void main(String[] args){
        try {

            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            TweetStream stream=twitterScraper.getTweetStream("Kashmir");
            stream.setStreamListener(new TweetStreamListener() {
                @Override
                public void onPageRefresh(List<Tweet> tweets) {
                    for(Tweet tweet:tweets){
                        System.out.println(tweet);
                    }
                }
            });
            stream.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
