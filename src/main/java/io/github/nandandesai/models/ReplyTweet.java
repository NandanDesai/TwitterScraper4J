package io.github.nandandesai.models;

import java.util.ArrayList;

public class ReplyTweet extends Tweet {
    private ArrayList<String> repliedTo=new ArrayList<>();

    public ReplyTweet(String tweetID, String authorUsername, String tweetText, String timestamp, ArrayList<Media> media, ArrayList<String> repliedTo) {
        super(tweetID, authorUsername, tweetText, timestamp, media);
        this.repliedTo=repliedTo;
    }
}
