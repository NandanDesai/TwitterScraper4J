package io.github.nandandesai.models;

import java.util.ArrayList;

public class Retweet extends Tweet{
    private String retweetedBy; //username of who retweeted
    private String comment=""; //retweet with comment

    public Retweet(String tweetID, String authorUsername, String tweetText, String timestamp, ArrayList<Media> media, String retweetedBy, String comment) {
        super(tweetID, authorUsername, tweetText, timestamp, media);
        this.retweetedBy=retweetedBy;
        this.comment=comment;
    }

    public Retweet(String tweetID, String authorUsername, String tweetText, String timestamp, ArrayList<Media> media, String retweetedBy) {
        super(tweetID, authorUsername, tweetText, timestamp, media);
        this.retweetedBy=retweetedBy;
    }

    public String getRetweetedBy() {
        return retweetedBy;
    }

    public String getComment(){
        return comment;
    }
}
