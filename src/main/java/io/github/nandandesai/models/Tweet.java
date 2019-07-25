package io.github.nandandesai.models;

import java.net.URL;
import java.util.ArrayList;

public class Tweet {
    private String tweetID;
    private String authorUsername;
    private String tweetText;
    private String timestamp; //better to have a Date datatype
    private ArrayList<Media> media;
    private ArrayList<String> mentions; //the list of @'s mentioned in the tweet
    private ArrayList<String> hashtags; //the list of #'s mentioned in the tweet
    private ArrayList<URL> urls; //the list of external URLs mentioned in the tweet

    public Tweet(String tweetID, String authorUsername, String tweetText, String timestamp, ArrayList<Media> media, ArrayList<String> mentions, ArrayList<String> hashtags, ArrayList<URL> urls) {
        this.tweetID = tweetID;
        this.authorUsername = authorUsername;
        this.tweetText = tweetText;
        this.timestamp = timestamp;
        this.media = media;
        this.mentions = mentions;
        this.hashtags = hashtags;
        this.urls = urls;
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

    public ArrayList<String> getMentions() {
        return mentions;
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    public ArrayList<URL> getUrls() {
        return urls;
    }

    @Override
    public String toString(){
        return "{\n" +
                "  \"tweetID\": \""+tweetID+"\",\n" +
                "  \"authorUsername\": \""+authorUsername+"\",\n" +
                "  \"tweetText\": \""+tweetText+"\",\n" +
                "  \"timestamp\": \""+timestamp+"\",\n" +
                "  \"media\": "+this.media.toString()+",\n" +
                "  \"mentions\": "+this.mentions.toString()+",\n" +
                "  \"hashtags\": "+this.hashtags.toString()+",\n" +
                "  \"urls\": "+this.urls.toString()+"\n" +
                "}";
    }
}

