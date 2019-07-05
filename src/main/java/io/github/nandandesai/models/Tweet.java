package io.github.nandandesai.models;

import java.util.ArrayList;

public class Tweet {
    private String tweetID;
    private String authorUsername;
    private String tweetText;
    private String timestamp; //better to have a Date datatype
    private ArrayList<Media> media;

    public Tweet(String tweetID, String authorUsername, String tweetText, String timestamp, ArrayList<Media> media) {
        this.tweetID = tweetID;
        this.authorUsername = authorUsername;
        this.tweetText = tweetText;
        this.timestamp = timestamp;
        this.media = media;
    }

    public String getTweetID() {
        return tweetID;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public String getTweetText() {
        return tweetText;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }
}

