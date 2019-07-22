package io.github.nandandesai;

import io.github.nandandesai.models.Media;
import io.github.nandandesai.models.Tweet;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class TweetScraper {

    List<Tweet> getHomeTimeline(String username, Map<String, String> cookies) throws IOException {
        if(username == null || username.equals("") || cookies == null){
            Logger.error(new IllegalArgumentException("\"username\" or \"cookies\" cannot be null or empty"));
            return null;
        }
        String url="https://mobile.twitter.com/i/nojs_router?path=/"+username;
        Logger.info("Fetching the profile using : "+url);
        Document doc = Utils.getDocument(url,cookies);

        String tweetID="";
        String authorUsername="";
        String tweetText="";
        String timestamp="";
        ArrayList<Media> media=new ArrayList<>();
        ArrayList<String> mentions=new ArrayList<>();
        ArrayList<String> hashtags=new ArrayList<>();
        ArrayList<URL> urls=new ArrayList<>();

        /*
        * TODO: find if it is a tweet or a retweet or reply
        *
        * */

        Elements tweetTables=doc.getElementsByClass("tweet  ");
        System.out.println();
        for(Element tweetTable : tweetTables){
            Element tweetTextDiv=tweetTable.getElementsByClass("tweet-text").first();
            tweetText=tweetTextDiv.text();
            System.out.println("Tweet Text : "+tweetText);
            tweetID=tweetTextDiv.attr("data-id");
            System.out.println("Tweet Id: "+tweetID);
            Elements linksAndHashtags=tweetTextDiv.getElementsByTag("a");

            if(linksAndHashtags!=null) {
                for (Element anchorTag : linksAndHashtags) {
                    Elements urlInTweet=anchorTag.getElementsByClass("twitter_external_link dir-ltr tco-link");
                    if(!urlInTweet.isEmpty()){
                        System.out.println("URL in Tweet: "+urlInTweet.attr("data-url"));
                        urls.add(new URL(urlInTweet.attr("data-url")));
                    }

                    Elements hashtagInTweet=anchorTag.getElementsByClass("twitter-hashtag dir-ltr");
                    if(!hashtagInTweet.isEmpty()){
                        System.out.println("Hashtag in Tweet: "+hashtagInTweet.text());
                        hashtags.add(hashtagInTweet.text());
                    }

                    Elements mentionsInTweet=anchorTag.getElementsByClass("twitter-atreply dir-ltr");
                    if(!mentionsInTweet.isEmpty()){
                        System.out.println("Mention in Tweet: "+mentionsInTweet.text());
                        mentions.add(mentionsInTweet.text());
                    }
                }
            }

            Element usernameDiv=tweetTable.getElementsByClass("username").first();
            authorUsername=usernameDiv.text();
            System.out.println("Author Username: "+authorUsername);

            Element timestampTd=tweetTable.getElementsByClass("timestamp").first();
            timestamp=timestampTd.text();
            System.out.println("Timestamp: "+timestamp);

            boolean isRetweet=!tweetTable.getElementsByClass("tweet-social-context").isEmpty();
            System.out.println("is Retweet: "+isRetweet);

            if(isRetweet){
                String retweetedBy=username;
                String comment="";

                //create the Retweet object here
            }


            boolean isReply=!tweetTable.getElementsByClass("tweet-reply-context username").isEmpty();
            System.out.println("is Reply: "+isReply);

            if(isReply){

            }

            System.out.println();
            System.out.println();
        }

        return null;
    }

    List<Tweet> getUpperThread(String username, String tweetId){
        ArrayList<Tweet> upperThread=new ArrayList<>();


        return upperThread;
    }
}
