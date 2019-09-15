package io.github.nandandesai;

import io.github.nandandesai.models.Tweet;

import java.util.List;

public interface TweetStreamListener {
    void onPageRefresh(List<Tweet> tweets);
}
