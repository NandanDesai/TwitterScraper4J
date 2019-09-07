package io.github.nandandesai.tests;

import io.github.nandandesai.tests.formal.Credentials;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class Twitter4JStream {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Credentials credentials=new Credentials();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(credentials.getApiKey())
                .setOAuthConsumerSecret(credentials.getApiSecretKey())
                .setOAuthAccessToken(credentials.getAccessToken())
                .setOAuthAccessTokenSecret(credentials.getAccessTokenSecret());
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        twitterStream.addListener(new StatusListener() {
            @Override
            public void onStatus(Status status) {
                System.out.println(status.getCreatedAt());
                System.out.println(status.getText());
                System.out.println();
                System.out.println();
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {

            }

            @Override
            public void onStallWarning(StallWarning warning) {

            }

            @Override
            public void onException(Exception ex) {

            }
        });

        FilterQuery tweetFilterQuery = new FilterQuery(); // See
        tweetFilterQuery.track(new String[]{"Kashmir"});

        twitterStream.filter(tweetFilterQuery);


    }
}
