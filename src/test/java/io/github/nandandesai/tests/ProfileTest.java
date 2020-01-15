package io.github.nandandesai.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.nandandesai.twitterscraper4j.TwitterScraper;
import io.github.nandandesai.twitterscraper4j.exceptions.TwitterException;
import io.github.nandandesai.twitterscraper4j.models.Profile;

import java.io.IOException;

public class ProfileTest {
    public static void main(String[] args){
        testProfileFetch("nandankdesai"); //protected
        testProfileFetch("fs0c131y"); //unverified
        testProfileFetch("realDonaldTrump"); //verified
        testProfileFetch("asdfasdfasdfasd121212"); //doesn't exists
        testProfileFetch("kashmirintel"); //suspended
        testProfileFetch("Gurmeetramrahim"); //withheld
    }

    private static void testProfileFetch(String username){
        try {
            TwitterScraper scraper = TwitterScraper.getInstance();
            Profile profile = scraper.getProfile(username);//fs0c131y
            displayProfile(profile);

        }catch (IOException io){
            io.printStackTrace();
        } catch (TwitterException e) {
            System.out.println("Http Status Code: "+e.getHttpStatusCode());
            e.printStackTrace();
        }
    }

    private static void displayProfile(Profile profile){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //System.out.println(gson.toJson(profile));
        System.out.println(profile);
    }
}
