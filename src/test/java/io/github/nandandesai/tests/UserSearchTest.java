package io.github.nandandesai.tests;

import io.github.nandandesai.TwitterScraper;
import io.github.nandandesai.models.UserSearchResult;

import java.io.IOException;
import java.util.List;

public class UserSearchTest {

    public static void main(String[] args){
        testUserSearch("CIA");
        testUserSearch("Ryan Higa");
        testUserSearch("Chowkidar");
    }


    private static void testUserSearch(String query){
        try {
            TwitterScraper scraper = TwitterScraper.getInstance();
            List<UserSearchResult> userSearchResults =scraper.searchUser(query);
            for (UserSearchResult result: userSearchResults){
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
