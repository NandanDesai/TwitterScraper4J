package io.github.nandandesai.models;

import java.net.URL;

public class Profile {
    private String name;
    private String username;
    private String description;
    private String location;
    private int noOfFollowers;
    private int noOfFriends; //no of people this person is following
    private boolean isVerified;
    private int noOfTweets;
    private URL profilePicLink;
    private URL largerProfilePicLink;
    private URL userWebsite;

    public Profile(String name, String username, String description, String location, int noOfFollowers, int noOfFriends, boolean isVerified, int noOfTweets, URL profilePicLink, URL largerProfilePicLink, URL userWebsite) {
        this.name = name;
        this.username = username;
        this.description = description;
        this.location = location;
        this.noOfFollowers = noOfFollowers;
        this.noOfFriends = noOfFriends;
        this.isVerified = isVerified;
        this.noOfTweets = noOfTweets;
        this.profilePicLink = profilePicLink;
        this.largerProfilePicLink = largerProfilePicLink;
        this.userWebsite = userWebsite;
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
}
