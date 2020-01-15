package io.github.nandandesai.twitterscraper4j;

import io.github.nandandesai.twitterscraper4j.models.Tweet;

import java.util.List;

public interface TweetStreamListener {
    void onPageRefresh(List<Tweet> tweets);
}
