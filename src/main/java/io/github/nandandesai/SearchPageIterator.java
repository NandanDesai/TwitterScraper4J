package io.github.nandandesai;

import io.github.nandandesai.exceptions.TwitterException;
import io.github.nandandesai.models.Tweet;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class SearchPageIterator implements Iterator<List<Tweet>> {
    private String nextUrl;
    private String prevUrl;
    private Map<String, String> cookies;
    private Document doc;

    SearchPageIterator(String keyword, Map<String, String> cookies) {
        this.cookies=cookies;
        keyword=keyword.replace(" ","%20");
        String link= null;
        link = "https://mobile.twitter.com/i/nojs_router?path="+ "/search?q="+keyword+"&s=typd&x=23&y=7";
        prevUrl="https://mobile.twitter.com/";
        nextUrl=link;
    }

    @Override
    public boolean hasNext() {
        try {
            if(nextUrl!=null) {
                System.out.println("Referer: "+prevUrl);
                System.out.println("nextUrl: "+nextUrl);
                this.doc = getDocument(nextUrl, cookies, prevUrl);
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
            Element refreshTd=doc.getElementsByClass("r").first();
            if(refreshTd!=null){
                prevUrl=nextUrl;
                System.out.println("refresh link: "+refreshTd.child(0).attr("href"));
                nextUrl="https://mobile.twitter.com/i/nojs_router?path="+refreshTd.child(0).attr("href");
            }else{
                nextUrl=null;
            }
            return tweets;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    Document getDocument(String url, Map cookies, String referer) throws IOException, TwitterException {
        Connection.Response response = Jsoup.connect(url).headers(Utils.getHttpHeaders()).ignoreHttpErrors(true).followRedirects(true)
                .method(Connection.Method.POST)
                .header("Referer", referer)
                .cookies(cookies)
                .execute();
        int statusCode = response.statusCode();
         if (statusCode != 200) {
            throw new TwitterException(statusCode, response.statusMessage());
        }
        return response.parse();
    }
}
