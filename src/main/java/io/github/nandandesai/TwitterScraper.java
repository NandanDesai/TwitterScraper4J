package io.github.nandandesai;

import io.github.nandandesai.models.Profile;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TwitterScraper {
    private static TwitterScraper scraper;
    private Map<String, String> cookies;
    private Connection.Response response;

    private TwitterScraper() throws IOException {
        response= Jsoup.connect("https://mobile.twitter.com").headers(getHttpHeaders()).ignoreHttpErrors(true).followRedirects(true).method(Connection.Method.GET).execute();
        cookies = response.cookies();
        Logger.info("Cookies: "+cookies.toString());
    }

    public static TwitterScraper getInstance() throws IOException {
        synchronized (TwitterScraper.class) {
            if (scraper == null) {
                scraper=new TwitterScraper();
            }
            return scraper;
        }
    }

    public Profile getProfile(String username) throws IOException {
        String url="https://mobile.twitter.com/i/nojs_router?path=/"+username;
        Logger.info("Fetching the profile using : "+url);
        response = Jsoup.connect(url).headers(getHttpHeaders()).ignoreHttpErrors(true).followRedirects(true)
                .method(Connection.Method.POST)
                .header("Referer","https://mobile.twitter.com/")
                .cookies(cookies)
                .execute();
        Document doc = response.parse();

        String name="";
        String description="";
        String location="";
        int noOfFollowers=0;
        int noOfFriends=0;
        boolean isVerified=false;
        int noOfTweets=0;
        URL profilePicLink=null;
        URL largerProfilePicLink=null;
        URL userWebsite=null;

        //get the profile <div> tag
        Element profileDiv=doc.getElementsByClass("profile").first();

        //getting Profile Pic URL
        Element avatarTd=profileDiv.getElementsByClass("avatar").first();
        String imgUrl=avatarTd.child(0).attr("src");
        Logger.info("Image URL: "+imgUrl);
        profilePicLink=new URL(imgUrl);
        String largerImgUrl=imgUrl.replace("normal","400x400");
        Logger.info("Larger Image URL: "+largerImgUrl);
        largerProfilePicLink=new URL(largerImgUrl);

        //getting Fullname of the user
        Element nameDiv=profileDiv.getElementsByClass("fullname").first();
        name=nameDiv.text();
        Logger.info("Name: "+name);
        try{
            //checking if the account is verified or not
            if(nameDiv.getElementsByClass("badge").first().child(0).attr("alt").equalsIgnoreCase("Verified Account")){
                isVerified=true;
            }
        }catch (NullPointerException npe){}
        Logger.info("isVerified: "+isVerified);

        //getting the user location
        Element locationDiv=profileDiv.getElementsByClass("location").first();
        location=locationDiv.text();
        Logger.info("Location: "+location);

        //getting the user description
        Element bioDiv=profileDiv.getElementsByClass("bio").first();
        description=bioDiv.text();
        Logger.info("Description: "+description);

        //getting the user website
        Element urlDiv=profileDiv.getElementsByClass("url").first();
        String website=urlDiv.getElementsByTag("a").first().attr("href");
        userWebsite=new URL(website);
        Logger.info("User website: "+website);

        Elements statsTds=profileDiv.getElementsByClass("stat");

        //getting the number of tweets
        Element statnumDiv=statsTds.get(0).getElementsByClass("statnum").first();
        noOfTweets=getProperInts(statnumDiv.text());
        Logger.info("Number of tweets: "+noOfTweets);

        //getting the number of friends
        statnumDiv=statsTds.get(1).getElementsByClass("statnum").first();
        noOfFriends=getProperInts(statnumDiv.text());
        Logger.info("Number of friends: "+noOfFriends);

        //getting the number of followers
        statnumDiv=statsTds.get(2).getElementsByClass("statnum").first();
        noOfFollowers=getProperInts(statnumDiv.text());
        Logger.info("Number of followers: "+noOfFollowers);


        return null;
    }

    private static Map<String, String> getHttpHeaders(){
        Logger.info("Using Mozilla headers. Trying to emulate a browser as much as possible.");
        Map<String, String> headers=new HashMap<>();
        headers.put("User-Agent","Mozilla/5.0 (X11; Linux x86_64; rv:58.0) Gecko/20100101 Firefox/58.0");
        headers.put("Accept-Language","en-US,en;q=0.5");
        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.put("Accept-Charset","utf-8");
        headers.put("Connection","keep-alive");
        headers.put("Upgrade-Insecure-Requests","1");
        return headers;
    }

    private int getProperInts(String commaSeparatedInts){
        String[] intVector=commaSeparatedInts.split(",");
        String intString="";
        for(int i=0; i<intVector.length; i++){
            intString=intString+intVector[i];
        }
        return Integer.parseInt(intString);
    }
}
