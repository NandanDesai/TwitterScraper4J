package io.github.nandandesai;

import io.github.nandandesai.models.UserSearchResult;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class SearchScraper {

    List<UserSearchResult> searchUser(String query, Map<String, String> cookies) throws IOException {
        if(query == null || query.equals("") || cookies == null){
            Logger.error(new IllegalArgumentException("\"query\" or \"cookies\" cannot be null or empty"));
            return null;
        }

        //even though I'm using URLEncoder later on in this code, it replaces spaces with '+' instead of "%20" which can be problematic for Twitter.
        //So, before formally encoding the URL, I'm replacing all spaces with "%20" manually and then giving that to the URLEncoder
        //Also, URLEncoder will later replace '%' sign with "%25". So, the resulting URL query will have %2520 instead of %20 for a space character.
        //But that is what Twitter was expecting while I tested it.
        query=query.replace(" ","%20");


        String urlPath="/search/users?q="+query+"&s=typd";

        //Now, formally encode URLs. Without this, Twitter won't understand the query
        urlPath= URLEncoder.encode(urlPath, StandardCharsets.UTF_8.name());

        String url="https://mobile.twitter.com/i/nojs_router?path="+urlPath;
        Logger.info("Searching for : \""+query+"\" with URL: "+url);
        Connection.Response response = Jsoup.connect(url).headers(TwitterScraper.getHttpHeaders()).ignoreHttpErrors(true).followRedirects(true)
                .method(Connection.Method.POST)
                .header("Referer","https://mobile.twitter.com/")
                .cookies(cookies)
                .execute();
        Document doc = response.parse();

        Element userListDiv=doc.getElementsByClass("user-list").first();

        Elements userItemsTables=userListDiv.getElementsByClass("user-item");

        ArrayList<UserSearchResult> userSearchResults =new ArrayList<>();

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

            userSearchResults.add(new UserSearchResult(username, name, profilePic, largeProfilePic, isVerified));

        }

        return userSearchResults;

    }
}
