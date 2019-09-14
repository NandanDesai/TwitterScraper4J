package io.github.nandandesai;

import io.github.nandandesai.models.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class Common {
    static List<Tweet> scrapeTweets(Document doc) throws IOException {


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
                Element retweetTd=tweetTable.getElementsByClass("tweet-social-context").first();
                String username=retweetTd.text().replace("retweeted","").trim();
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

    static List<User> scrapeUsers(Document doc) throws MalformedURLException {

        ArrayList<User> users =new ArrayList<>();
        try {
            Element userListDiv=doc.getElementsByClass("user-list").first();
            Elements userItemsTables = userListDiv.getElementsByClass("user-item");



        for(Element userItemTable : userItemsTables){
            String username="";
            String name="";
            URL profilePic=null;
            URL largeProfilePic=null;
            boolean isVerified=false;

            username=userItemTable.getElementsByClass("username").first().text();
            name=userItemTable.getElementsByClass("fullname").first().text();
            String imgUrl=userItemTable.getElementsByClass("profile-image").attr("src");
            profilePic=new URL(imgUrl);
            String largerImgUrl=imgUrl.replace("normal","400x400");
            largeProfilePic=new URL(largerImgUrl);

            Element verifiedImg=userItemTable.getElementsByAttributeValue("alt","Verified Account").first();
            if(verifiedImg!=null){
                isVerified=true;
            }

            users.add(new User(username, name, profilePic, largeProfilePic, isVerified));

        }


        }catch (Exception e){
            e.printStackTrace();
            System.out.println(doc);
        }
        return users;
    }
}
