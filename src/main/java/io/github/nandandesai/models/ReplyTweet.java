package io.github.nandandesai.models;

import java.net.URL;
import java.util.ArrayList;

public class ReplyTweet extends Tweet {
    private String repliedToTweetID; //the tweet ID of the tweet to which the replytweet is being made
    private ArrayList<String> repliedTo=new ArrayList<>();

    public ReplyTweet(String tweetID, String authorUsername, String tweetText, String timestamp, ArrayList<Media> media, ArrayList<String> mentions, ArrayList<String> hashtags, ArrayList<URL> urls, ArrayList<String> repliedTo) {
        super(tweetID, authorUsername, tweetText, timestamp, media, mentions, hashtags, urls);
        this.repliedTo=repliedTo;
    }

    public String getRepliedToTweetID() {
        return repliedToTweetID;
    }

    public ArrayList<String> getRepliedTo() {
        return repliedTo;
    }
}
