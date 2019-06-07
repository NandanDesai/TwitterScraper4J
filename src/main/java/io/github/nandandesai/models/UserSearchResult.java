package io.github.nandandesai.models;

import java.net.URL;

public class UserSearchResult {

    private String username;
    private String name;
    private URL profilePic;
    private URL largeProfilePic;
    private boolean isVerified;

    public UserSearchResult(String username, String name, URL profilePic, URL largeProfilePic, boolean isVerified) {
        this.username = username;
        this.name = name;
        this.profilePic = profilePic;
        this.largeProfilePic = largeProfilePic;
        this.isVerified = isVerified;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public URL getProfilePic() {
        return profilePic;
    }

    public URL getLargeProfilePic() {
        return largeProfilePic;
    }

    public boolean isVerified() {
        return isVerified;
    }

    @Override
    public String toString(){
        return "{\n" +
                "  \"username\": \""+username+"\",\n" +
                "  \"name\": \""+name+"\",\n" +
                "  \"profilePic\": \""+profilePic+"\",\n" +
                "  \"largeProfilePic\": \""+largeProfilePic+"\",\n" +
                "  \"isVerified\": "+isVerified+"\n" +
                "}";
    }
}
