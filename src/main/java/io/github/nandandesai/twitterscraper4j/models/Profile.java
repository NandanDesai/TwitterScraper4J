package io.github.nandandesai.twitterscraper4j.models;

import java.net.URL;

/**
 * A class that holds all the profile information about the user
 * @author Nandan Desai
 */
public class Profile {
    private String name;
    private String username;
    private String description;
    private String location;
    private int noOfFollowers;
    private int noOfFriends; //no of people this person is following
    private boolean isVerified;
    private boolean isProtected;
    private int noOfTweets;
    private URL profilePicLink;
    private URL largerProfilePicLink;
    private URL userWebsite;

    public Profile(String name, String username, String description, String location, int noOfFollowers, int noOfFriends, boolean isVerified, boolean isProtected, int noOfTweets, URL profilePicLink, URL largerProfilePicLink, URL userWebsite) {
        this.name = name;
        this.username = username;
        this.description = description;
        this.location = location;
        this.noOfFollowers = noOfFollowers;
        this.noOfFriends = noOfFriends;
        this.isVerified = isVerified;
        this.isProtected = isProtected;
        this.noOfTweets = noOfTweets;
        this.profilePicLink = profilePicLink;
        this.largerProfilePicLink = largerProfilePicLink;
        this.userWebsite = userWebsite;
    }


    public boolean isProtected() {
        return isProtected;
    }

    public String getLocation() {
        return location;
    }

    public URL getLargerProfilePicLink() {
        return largerProfilePicLink;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername(){
        return this.username;
    }

    public String getDescription() {
        return this.description;
    }

    public int getNoOfFollowers() {
        return this.noOfFollowers;
    }

    public int getNoOfFriends(){
        return this.noOfFriends;
    }

    public boolean isVerified() {
        return this.isVerified;
    }

    public int getNoOfTweets(){
        return this.noOfTweets;
    }

    public URL getProfilePicLink(){
        return this.profilePicLink;
    }

    public URL getUserWebsite() {
        return this.userWebsite;
    }

    @Override
    public String toString(){
        return "{\n" +
                "  \"name\": \""+name+"\",\n" +
                "  \"username\": \""+username+"\",\n" +
                "  \"description\": \""+description+"\",\n" +
                "  \"location\": \""+location+"\",\n" +
                "  \"noOfFollowers\": "+noOfFollowers+",\n" +
                "  \"noOfFriends\": "+noOfFriends+",\n" +
                "  \"isVerified\": "+isVerified+",\n" +
                "  \"isProtected\": "+isProtected+",\n" +
                "  \"noOfTweets\": "+noOfTweets+",\n" +
                "  \"profilePicLink\": \""+profilePicLink+"\",\n" +
                "  \"largerProfilePicLink\": \""+largerProfilePicLink+"\",\n" +
                "  \"userWebsite\": \""+userWebsite+"\"\n" +
                "}";
    }
}
