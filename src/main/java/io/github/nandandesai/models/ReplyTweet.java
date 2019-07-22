package io.github.nandandesai.models;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReplyTweet extends Tweet {
    private List<Tweet> upperThread; //the series of tweets that appear above the present tweet in the thread.
                                     //This means that even if there are tweets below this tweet in the same thread, they are not considered here.

    public ReplyTweet(String tweetID, String authorUsername, String tweetText, String timestamp, ArrayList<Media> media, ArrayList<String> mentions, ArrayList<String> hashtags, ArrayList<URL> urls, List<Tweet> upperThread) {
        super(tweetID, authorUsername, tweetText, timestamp, media, mentions, hashtags, urls);
        this.upperThread=upperThread;
    }

    public List<Tweet> getUpperThread() {
        return upperThread;
    }
}
