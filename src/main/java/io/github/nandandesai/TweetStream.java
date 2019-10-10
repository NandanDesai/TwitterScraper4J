package io.github.nandandesai;

import io.github.nandandesai.models.Tweet;

import java.util.List;

public class TweetStream {
    private TweetStreamListener streamListener;

    private TweetStreamIterator streamIterator;
    private volatile boolean stop=false;
    TweetStream(TweetStreamIterator streamIterator){
        this.streamIterator=streamIterator;
    }

    public void setStreamListener(TweetStreamListener streamListener){
        this.streamListener=streamListener;
    }

    public void start(){
        if(streamListener==null){
            throw new IllegalArgumentException("TweetStreamListener is not set.");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(streamIterator.hasNext() && !stop){
                    List<Tweet> tweets=streamIterator.next();
                    streamListener.onPageRefresh(tweets);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    //TODO: stopping the stream
    public void stop(){
        stop=true;
    }
}

