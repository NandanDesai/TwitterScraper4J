package io.github.nandandesai.tests;

import io.github.nandandesai.TwitterScraper;
import io.github.nandandesai.models.UserSearchResult;

import java.io.IOException;
import java.util.List;

public class UserSearchTest {

    public static void main(String[] args){
        testPeopleSearch("CIA");
    }


    private static void testPeopleSearch(String query){
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
