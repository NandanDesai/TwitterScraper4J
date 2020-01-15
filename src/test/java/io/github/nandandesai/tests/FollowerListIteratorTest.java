package io.github.nandandesai.tests;

import io.github.nandandesai.twitterscraper4j.TwitterScraper;
import io.github.nandandesai.twitterscraper4j.models.User;

import java.util.Iterator;
import java.util.List;

public class FollowerListIteratorTest {
    public static void main(String[] args){
        test("realDonaldTrump");
    }

    static void test(String username){
        int followerCount=0;
        try {

            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            Iterator<List<User>> it=twitterScraper.getAllFollowers(username);
            while(it.hasNext()){
                List<User> users=it.next();
                for(User user:users){
                    System.out.println(user);
                    followerCount++;
                }
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("No of followers displayed: "+followerCount);
    }
}
