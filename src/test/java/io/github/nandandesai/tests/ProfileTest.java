package io.github.nandandesai.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.nandandesai.TwitterScraper;
import io.github.nandandesai.models.Profile;

import java.io.IOException;

public class ProfileTest {
    public static void main(String[] args){
        testProfileFetch("nandankdesai"); //protected
        testProfileFetch("fs0c131y"); //unverified
        testProfileFetch("realDonaldTrump"); //verified
        testProfileFetch("asdfasdfasdfasd121212"); //doesn't exists
    }

    private static void testProfileFetch(String username){
        try {
            TwitterScraper scraper = TwitterScraper.getInstance();
            Profile profile = scraper.getProfile(username);//fs0c131y
            if (profile == null) {
                System.out.println("Profile doesn't exists for username: " + username);
            } else {
                displayProfile(profile);
            }
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    private static void displayProfile(Profile profile){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //System.out.println(gson.toJson(profile));
        System.out.println(profile);
    }
}
