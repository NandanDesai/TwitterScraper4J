package io.github.nandandesai.models;

public class Media {
    public enum TYPE{
        PICTURE, VIDEO, GIF
    }
    private TYPE type;
    private String link;

    public Media(TYPE type, String link) {
        this.type = type;
        this.link = link;
    }

    public TYPE getType() {
        return type;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString(){
        return "{\n" +
                "  \"type\": \""+type+"\",\n" +
                "  \"url\": "+link+"\n" +
                "}";
    }
}
