package io.github.nandandesai.tests;

import io.github.nandandesai.tests.formal.Credentials;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class Twitter4JSetup {

    public static void main(String[] args) throws IOException, URISyntaxException {


        Credentials credentials=new Credentials();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(credentials.getApiKey())
                .setOAuthConsumerSecret(credentials.getApiSecretKey())
                .setOAuthAccessToken(credentials.getAccessToken())
                .setOAuthAccessTokenSecret(credentials.getAccessTokenSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {
            User userAPI=twitter.showUser("Gurmeetramrahim");
            System.out.println(userAPI.getName());
            System.out.println(userAPI.getWithheldInCountries()[0]);

        } catch (TwitterException e) {
            System.out.println(e.getErrorMessage());
            e.printStackTrace();
        }
    }

}
