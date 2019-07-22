package io.github.nandandesai;

import io.github.nandandesai.models.Profile;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


/**
 * This class is package-private and cannot be used outside this library.
 * This class does all the heavy duty work of scraping Twitter user's profile data
 */
class ProfileScraper {

    /**
     * scrapes profile data from twitter
     * @param username Twitter username
     * @param cookies cookies fetched from Twitter upon initialization of TwitterScraper class
     * @return profile data if exists else returns null.
     * @throws IOException
     */
    Profile getProfile(String username, Map<String, String> cookies) throws IOException {
        if(username == null || username.equals("") || cookies == null){
            Logger.error(new IllegalArgumentException("\"username\" or \"cookies\" cannot be null or empty"));
            return null;
        }
        String url="https://mobile.twitter.com/i/nojs_router?path=/"+username;
        Logger.info("Fetching the profile using : "+url);
        Document doc = Utils.getDocument(url,cookies);

        String name="";
        String description="";
        String location="";
        int noOfFollowers=0;
        int noOfFriends=0;
        boolean isVerified=false;
        boolean isProtected=false;
        int noOfTweets=0;
        URL profilePicLink=null;
        URL largerProfilePicLink=null;
        URL userWebsite=null;


        //get the profile <div> tag
        Element profileDiv=doc.getElementsByClass("profile").first();

        //if profileDiv doesn't exists, then that means the Profile also doesn't exists.  Return null in that case.
        if(profileDiv==null){
            return null;
        }

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

        Element protectedDiv=profileDiv.getElementsByClass("protected").first();
        if(protectedDiv!=null){
            isProtected=true;
        }
        Logger.info("isProtected: "+isProtected);

        //getting the user location
        Element locationDiv=profileDiv.getElementsByClass("location").first();
        location=locationDiv.text();
        Logger.info("Location: "+location);

        //getting the user description
        Element bioDiv=profileDiv.getElementsByClass("bio").first();
        description=bioDiv.text();
        Logger.info("Description: "+description);

        try {
            //getting the user website
            Element urlDiv = profileDiv.getElementsByClass("url").first();
            String website = urlDiv.getElementsByTag("a").first().attr("href");
            userWebsite = new URL(website);
            Logger.info("User website: " + website);
        }catch (MalformedURLException mue){
            Logger.error(mue);
        }

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


        return new Profile(name, username, description, location, noOfFollowers, noOfFriends, isVerified, isProtected, noOfTweets, profilePicLink, largerProfilePicLink, userWebsite);
    }

    //takes comma separated integers as input converts them into proper usable integers
    private int getProperInts(String commaSeparatedInts){
        String[] intVector=commaSeparatedInts.split(",");
        String intString="";
        for(int i=0; i<intVector.length; i++){
            intString=intString+intVector[i];
        }
        return Integer.parseInt(intString);
    }
}
