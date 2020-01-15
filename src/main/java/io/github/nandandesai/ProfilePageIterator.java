package io.github.nandandesai;

import io.github.nandandesai.exceptions.TwitterException;
import io.github.nandandesai.models.Tweet;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.Proxy;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class ProfilePageIterator implements Iterator<List<Tweet>> {

    private String nextUrl;
    private Map<String, String> cookies;
    private Document doc;
    private Proxy proxy;

    ProfilePageIterator(String username, Map<String, String> cookies, Proxy proxy) {
        this.proxy=proxy;
        this.cookies=cookies;
        if (username == null || username.equals("") || cookies == null) {
            throw new IllegalArgumentException("\"username\" or \"cookies\" cannot be null or empty");
        }
        nextUrl = "https://mobile.twitter.com/i/nojs_router?path=/" + username;
    }

    @Override
    public boolean hasNext() {
        try {
            if(nextUrl!=null) {
                this.doc = Utils.getDocument(nextUrl, cookies, this.getClass(), proxy);
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (TwitterException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Tweet> next() {
        try {
            List<Tweet> tweets=Common.scrapeTweets(doc);
            Element moreTweetDiv=doc.getElementsByClass("w-button-more").first();
            if(moreTweetDiv!=null){
                nextUrl="https://mobile.twitter.com/i/nojs_router?path="+moreTweetDiv.child(0).attr("href");
            }else{
                nextUrl=null;
            }
            return tweets;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
