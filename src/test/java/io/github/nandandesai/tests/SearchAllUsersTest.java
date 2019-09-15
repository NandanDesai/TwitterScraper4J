package io.github.nandandesai.tests;

import io.github.nandandesai.TwitterScraper;
import io.github.nandandesai.models.User;

import java.util.Iterator;
import java.util.List;

public class SearchAllUsersTest {

    public static void main(String[] args){
        test("BJP");
    }

    static void test(String query){
        int userCount=0;
        try {

            TwitterScraper twitterScraper = TwitterScraper.getInstance();
            Iterator<List<User>> it=twitterScraper.searchAllUsers(query);
            while(it.hasNext()){
                List<User> users=it.next();
                for(User user:users){
                    System.out.println(user);
                    userCount++;
                }
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("No of users displayed: "+userCount);
    }

}
