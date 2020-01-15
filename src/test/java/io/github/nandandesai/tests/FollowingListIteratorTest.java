package io.github.nandandesai.tests;

import io.github.nandandesai.twitterscraper4j.TwitterScraper;
import io.github.nandandesai.twitterscraper4j.models.User;

import java.util.Iterator;
import java.util.List;

public class FollowingListIteratorTest {

    public static void main(String[] args){
        test("realDonaldTrump");
    }

    static void test(String username){
        int friendsCount=0;
        try {

            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            Iterator<List<User>> it=twitterScraper.getAllFriends(username);
            while(it.hasNext()){
                List<User> users=it.next();
                for(User user:users){
                    System.out.println(user);
                    friendsCount++;
                }
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("No of friends displayed: "+friendsCount);
    }
}
