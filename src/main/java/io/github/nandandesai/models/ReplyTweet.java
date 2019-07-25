package io.github.nandandesai.models;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReplyTweet extends Tweet {
    private List<String> replyingTo;

    public ReplyTweet(String tweetID, String authorUsername, String tweetText, String timestamp, ArrayList<Media> media, ArrayList<String> mentions, ArrayList<String> hashtags, ArrayList<URL> urls, List<String> replyingTo) {
        super(tweetID, authorUsername, tweetText, timestamp, media, mentions, hashtags, urls);
        this.replyingTo=replyingTo;
    }

    public List<String> getReplyingTo() {
        return replyingTo;
    }

    @Override
    public String toString(){

        return "{\n" +
                "  \"tweetID\": \""+getTweetID()+"\",\n" +
                "  \"authorUsername\": \""+getAuthorUsername()+"\",\n" +
                "  \"tweetText\": \""+getTweetText()+"\",\n" +
                "  \"timestamp\": \""+getTimestamp()+"\",\n" +
                "  \"media\": "+getMedia().toString()+",\n" +
                "  \"mentions\": "+getMentions().toString()+",\n" +
                "  \"hashtags\": "+getHashtags().toString()+",\n" +
                "  \"urls\": "+getUrls().toString()+"\n" +
                "  \"replyingTo\": "+replyingTo.toString()+"\n" +
                "}";
    }
}
