package io.github.nandandesai.exceptions;

public class TwitterException extends Exception {
    private int httpStatusCode=200;

    public TwitterException(int httpStatusCode, String errorMessage){
        super(errorMessage);
        this.httpStatusCode=httpStatusCode;
    }

    public TwitterException(String errorMessage){
        super(errorMessage);
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
