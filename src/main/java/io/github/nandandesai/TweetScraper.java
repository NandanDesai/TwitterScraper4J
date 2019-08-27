package io.github.nandandesai;

import io.github.nandandesai.exceptions.TwitterException;
import io.github.nandandesai.models.Media;
import io.github.nandandesai.models.ReplyTweet;
import io.github.nandandesai.models.Retweet;
import io.github.nandandesai.models.Tweet;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class TweetScraper {

    private Map<String, String> cookies;

    public TweetScraper(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    List<Tweet> getHomeTimeline(String username) throws IOException, TwitterException {
        if (username == null || username.equals("") || cookies == null) {
            throw new IllegalArgumentException("\"username\" or \"cookies\" cannot be null or empty");
        }
        String url = "https://mobile.twitter.com/i/nojs_router?path=/" + username;
        Document doc = Utils.getDocument(url, cookies, TweetScraper.class);
        List<Tweet> tweets=new ArrayList<>();

        Elements tweetTables = doc.getElementsByClass("tweet  ");
        for (Element tweetTable : tweetTables) {
            String tweetID = "";
            String authorUsername = "";
            String tweetText = "";
            String timestamp = "";
            ArrayList<Media> media = new ArrayList<>();
            ArrayList<String> mentions = new ArrayList<>();
            ArrayList<String> hashtags = new ArrayList<>();
            ArrayList<URL> urls = new ArrayList<>();

            Element tweetTextDiv = tweetTable.getElementsByClass("tweet-text").first();
            tweetText = tweetTextDiv.text();
            tweetID = tweetTextDiv.attr("data-id");
            Elements linksAndHashtags = tweetTextDiv.getElementsByTag("a");

            if (linksAndHashtags != null) {
                for (Element anchorTag : linksAndHashtags) {
                    Elements urlInTweet = anchorTag.getElementsByClass("twitter_external_link dir-ltr tco-link");
                    if (!urlInTweet.isEmpty()) {
                        urls.add(new URL(urlInTweet.attr("data-url")));
                    }

                    Elements hashtagInTweet = anchorTag.getElementsByClass("twitter-hashtag dir-ltr");
                    if (!hashtagInTweet.isEmpty()) {
                        hashtags.add(hashtagInTweet.text());
                    }

                    Elements mentionsInTweet = anchorTag.getElementsByClass("twitter-atreply dir-ltr");
                    if (!mentionsInTweet.isEmpty()) {
                        mentions.add(mentionsInTweet.text());
                    }
                }
            }

            Element usernameDiv = tweetTable.getElementsByClass("username").first();
            authorUsername = usernameDiv.text();

            Element timestampTd = tweetTable.getElementsByClass("timestamp").first();
            timestamp = timestampTd.text();


            //this works for non-commented retweets only
            //TODO: complete Retweet stuff for 'retweet with comment'
            boolean isRetweet = !tweetTable.getElementsByClass("tweet-social-context").isEmpty();

            boolean isReply = !tweetTable.getElementsByClass("tweet-reply-context username").isEmpty();

            if (isRetweet) {
                String retweetedBy = username;
                String comment = ""; //complete this

                tweets.add(new Retweet(tweetID, authorUsername, tweetText, timestamp, media, mentions, hashtags, urls, retweetedBy, comment));

            }else if (isReply) {

                List<String> replyingTo=new ArrayList<>();

                Element replyDiv=tweetTable.getElementsByClass("tweet-reply-context username").first();
                Elements replyingToTags=replyDiv.getElementsByTag("a");

                for(Element replyUsernameTag:replyingToTags){
                    String textInTag=replyUsernameTag.text();
                    if(textInTag.contains("@")){
                        replyingTo.add(textInTag);
                    }
                }

                tweets.add(new ReplyTweet(tweetID, authorUsername, tweetText, timestamp, media, mentions, hashtags, urls, replyingTo));

            }else{

                tweets.add(new Tweet(tweetID, authorUsername, tweetText, timestamp, media, mentions, hashtags, urls));

            }
        }

        return tweets;
    }


    //TODO: get information about a single tweet https://mobile.twitter.com/a/status/<id>
    Tweet getTweet(String tweetId) throws IOException, TwitterException {


        String url = "https://mobile.twitter.com/i/nojs_router?path=/a/status/" + tweetId;
        Document doc = Utils.getDocument(url, cookies, TweetScraper.class);

        String tweetID = "";
        String authorUsername = "";
        String tweetText = "";
        String timestamp = "";
        ArrayList<Media> media = new ArrayList<>();
        ArrayList<String> mentions = new ArrayList<>();
        ArrayList<String> hashtags = new ArrayList<>();
        ArrayList<URL> urls = new ArrayList<>();
        Element tweetTable=doc.getElementsByClass("main-tweet").first();
        Element tweetTextDiv = tweetTable.getElementsByClass("tweet-text").first();
        tweetText = tweetTextDiv.text();
        tweetID = tweetTextDiv.attr("data-id");
        Elements linksAndHashtags = tweetTextDiv.getElementsByTag("a");

        if (linksAndHashtags != null) {
            for (Element anchorTag : linksAndHashtags) {
                Elements urlInTweet = anchorTag.getElementsByClass("twitter_external_link dir-ltr tco-link");
                if (!urlInTweet.isEmpty()) {
                    urls.add(new URL(urlInTweet.attr("data-url")));
                }

                Elements hashtagInTweet = anchorTag.getElementsByClass("twitter-hashtag dir-ltr");
                if (!hashtagInTweet.isEmpty()) {
                    hashtags.add(hashtagInTweet.text());
                }

                Elements mentionsInTweet = anchorTag.getElementsByClass("twitter-atreply dir-ltr");
                if (!mentionsInTweet.isEmpty()) {
                    mentions.add(mentionsInTweet.text());
                }
            }
        }

        Element usernameDiv = tweetTable.getElementsByClass("username").first();
        authorUsername = usernameDiv.text();

        Element timestampTd = tweetTable.getElementsByClass("metadata").first();
        timestamp = timestampTd.text();


        return new Tweet(tweetID, authorUsername, tweetText, timestamp, media, mentions, hashtags, urls);
    }

    List<Tweet> getUpperThread(String tweetId) throws IOException, TwitterException {
        ArrayList<Tweet> upperThread = new ArrayList<>();
        String url = "https://mobile.twitter.com/i/nojs_router?path=/a/status/" + tweetId;
        Document doc = Utils.getDocument(url, cookies, TweetScraper.class);


        Element inReplyTos = doc.getElementsByClass("timeline inreplytos").first();
        Elements tweetTables = inReplyTos.getElementsByClass("tweet  ");
        for (Element tweetTable : tweetTables) {
            String tweetID = "";
            String authorUsername = "";
            String tweetText = "";
            String timestamp = "";
            ArrayList<Media> media = new ArrayList<>();
            ArrayList<String> mentions = new ArrayList<>();
            ArrayList<String> hashtags = new ArrayList<>();
            ArrayList<URL> urls = new ArrayList<>();

            Element tweetTextDiv = tweetTable.getElementsByClass("tweet-text").first();
            tweetText = tweetTextDiv.text();
            tweetID = tweetTextDiv.attr("data-id");
            Elements linksAndHashtags = tweetTextDiv.getElementsByTag("a");

            if (linksAndHashtags != null) {
                for (Element anchorTag : linksAndHashtags) {
                    Elements urlInTweet = anchorTag.getElementsByClass("twitter_external_link dir-ltr tco-link");
                    if (!urlInTweet.isEmpty()) {
                        urls.add(new URL(urlInTweet.attr("data-url")));
                    }

                    Elements hashtagInTweet = anchorTag.getElementsByClass("twitter-hashtag dir-ltr");
                    if (!hashtagInTweet.isEmpty()) {
                        hashtags.add(hashtagInTweet.text());
                    }

                    Elements mentionsInTweet = anchorTag.getElementsByClass("twitter-atreply dir-ltr");
                    if (!mentionsInTweet.isEmpty()) {
                        mentions.add(mentionsInTweet.text());
                    }
                }
            }

            Element usernameDiv = tweetTable.getElementsByClass("username").first();
            authorUsername = usernameDiv.text();

            Element timestampTd = tweetTable.getElementsByClass("timestamp").first();
            timestamp = timestampTd.text();

            upperThread.add(new Tweet(tweetID, authorUsername, tweetText, timestamp, media, mentions, hashtags, urls));

        }
        return upperThread;
    }
}

