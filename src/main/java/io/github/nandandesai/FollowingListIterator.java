package io.github.nandandesai;

import io.github.nandandesai.exceptions.TwitterException;
import io.github.nandandesai.models.User;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class FollowingListIterator  implements Iterator<List<User>> {

    private String nextUrl;
    private String prevUrl;
    private Map<String, String> cookies;
    private Document doc;
    private boolean click=false;

    FollowingListIterator(String username, Map<String, String> cookies){
        this.cookies=cookies;
        if (username == null || username.equals("") || cookies == null) {
            throw new IllegalArgumentException("\"username\" or \"cookies\" cannot be null or empty");
        }
        prevUrl="https://mobile.twitter.com/";
        nextUrl = "https://mobile.twitter.com/i/nojs_router?path=/"+username+"/following";
    }

    @Override
    public boolean hasNext() {
        try {
            if(nextUrl!=null) {
                if(!click) {
                    this.doc = getFirstDocument();
                    click=true;
                }else{
                    this.doc = getNextDocument();
                }
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
    public List<User> next() {
        try {
            List<User> tweets=Common.scrapeUsers(doc);
            Element moreTweetDiv=doc.getElementsByClass("w-button-more").first();
            if(moreTweetDiv!=null){
                prevUrl=nextUrl;
                nextUrl="https://mobile.twitter.com"+moreTweetDiv.child(0).attr("href");
            }else{
                nextUrl=null;
            }
            return tweets;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Document getFirstDocument() throws IOException, TwitterException {
        Connection.Response response = Jsoup.connect(nextUrl).headers(Utils.getHttpHeaders()).ignoreHttpErrors(true).followRedirects(true)
                .method(Connection.Method.POST)
                .header("Referer", prevUrl)
                .cookies(cookies)
                .execute();
        this.cookies=response.cookies();
        int statusCode = response.statusCode();
        if (statusCode == 404) {
            throw new TwitterException(404, "User not found.");
        } else if (statusCode != 200) {
            throw new TwitterException(statusCode, response.statusMessage());
        }
        return response.parse();
    }

    private Document getNextDocument() throws IOException, TwitterException {
        Connection.Response response = Jsoup.connect(nextUrl).headers(Utils.getHttpHeaders()).ignoreHttpErrors(true).followRedirects(true)
                .method(Connection.Method.GET)
                .header("Referer", prevUrl)
                .header("Cache-Control"," max-age=0, no-cache")
                .header("Pragma", "no-cache")
                .cookies(cookies)
                .execute();
        System.out.println(response.url());
        System.out.println(cookies);
        int statusCode = response.statusCode();
        if (statusCode == 404) {
            throw new TwitterException(404, "User not found.");
        } else if (statusCode != 200) {
            throw new TwitterException(statusCode, response.statusMessage());
        }

        return response.parse();
    }
}
