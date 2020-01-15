package io.github.nandandesai;

import io.github.nandandesai.exceptions.TwitterException;
import io.github.nandandesai.models.Media;
import io.github.nandandesai.models.Tweet;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class TweetScraper {

    private Map<String, String> cookies;

    TweetScraper(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    List<Tweet> getHomeTimeline(String username, Proxy proxy) throws IOException, TwitterException {

        if (username == null || username.equals("") || cookies == null) {
            throw new IllegalArgumentException("\"username\" or \"cookies\" cannot be null or empty");
        }
        String url = "https://mobile.twitter.com/i/nojs_router?path=/" + username;
        Document doc = Utils.getDocument(url, cookies, TweetScraper.class, proxy);
        return Common.scrapeTweets(doc);
    }


    Tweet getTweet(String tweetId, Proxy proxy) throws IOException, TwitterException {
        String url = "https://mobile.twitter.com/i/nojs_router?path=/a/status/" + tweetId;
        Document doc = Utils.getDocument(url, cookies, TweetScraper.class, proxy);

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

        Element mediaDiv=tweetTable.getElementsByClass("media").first();
        String imgSrc=mediaDiv.child(0).attr("src");
        if(imgSrc.contains("media")){
            media.add(new Media(Media.TYPE.PICTURE,imgSrc));
        }else if(imgSrc.contains("tweet_video_thumb")){
            String[] parts=imgSrc.split("/");
            String lastPart=parts[4];
            //System.out.println(lastPart);
            String gifId=lastPart.split("\\.")[0];
            String gifUrl="https://video.twimg.com/tweet_video/"+gifId+".mp4";
            media.add(new Media(Media.TYPE.GIF,gifUrl));
        }else if(imgSrc.contains("ext_tw_video_thumb")){
            media.add(new Media(Media.TYPE.VIDEO,imgSrc));
        }
        return new Tweet(tweetID, authorUsername, tweetText, timestamp, media, mentions, hashtags, urls);
    }

    List<Tweet> getUpperThread(String tweetId, Proxy proxy) throws IOException, TwitterException {
        ArrayList<Tweet> upperThread = new ArrayList<>();
        String url = "https://mobile.twitter.com/i/nojs_router?path=/a/status/" + tweetId;
        Document doc = Utils.getDocument(url, cookies, TweetScraper.class, proxy);


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

