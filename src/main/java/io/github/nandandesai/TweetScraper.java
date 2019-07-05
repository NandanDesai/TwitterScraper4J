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
        Connection.Response response = Jsoup.connect(url).headers(TwitterScraper.getHttpHeaders()).ignoreHttpErrors(true).followRedirects(true)
                .method(Connection.Method.POST)
                .header("Referer","https://mobile.twitter.com/")
                .cookies(cookies)
                .execute();
        Document doc = response.parse();

        String tweetID="";
        String authorUsername="";
        String tweetText="";
        String timestamp="";
        ArrayList<Media> media=new ArrayList<>();
        ArrayList<String> mentions=new ArrayList<>();
        ArrayList<String> hashtags=new ArrayList<>();
        ArrayList<URL> urls=new ArrayList<>();

        Elements tweetTables=doc.getElementsByClass("tweet  ");

        for(Element tweetTable : tweetTables){
            Element tweetTextDiv=tweetTable.getElementsByClass("tweet-text").first();
            Logger.info("Tweet Text : "+tweetTextDiv.text());
            Logger.info("Tweet Id: "+tweetTextDiv.attr("data-id"));
            System.out.println();
            System.out.println();
        }

        return null;
    }
}
