package io.github.nandandesai.tests;

import io.github.nandandesai.TwitterScraper;
import io.github.nandandesai.exceptions.TwitterException;
import io.github.nandandesai.models.User;

import java.io.IOException;
import java.util.List;

public class UserSearchTest {

    public static void main(String[] args){
        testUserSearch("CIA");
        testUserSearch("Ryan Higa");
        testUserSearch("Chowkidar");
        testUserSearch("asdlhkckhikdf");
    }


    private static void testUserSearch(String query){
        try {
            TwitterScraper scraper = TwitterScraper.getInstance();
            List<User> users =scraper.searchUser(query);
            for (User result: users){
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

}
