package io.github.nandandesai.tests;

import io.github.nandandesai.twitterscraper4j.TwitterScraper;
import io.github.nandandesai.twitterscraper4j.models.Tweet;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Iterator;
import java.util.List;

public class ProxyTest {
    public static void main(String[] args){
        getTweetwithProxyTest();
        //getAllTweetsWithProxyTest("fs0c131y");
    }

    private static void getTweetwithProxyTest(){
        try {
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 9150));
            TwitterScraper twitterScraper = TwitterScraper.builder().proxy(proxy).build();
            System.out.println(twitterScraper.getTweet("1182159712998973440"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void getAllTweetsWithProxyTest(String username){
        int tweetCount=0;
        try {
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 9150));
            TwitterScraper twitterScraper = TwitterScraper.builder().proxy(proxy).build();
            Iterator<List<Tweet>> it=twitterScraper.getAllTweets(username);
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
