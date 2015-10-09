package main.interfaces;

import main.pojo.Tweet;

/**
 * Created by thomas on 06/10/15.
 */
public interface OnTweetSelectedListener {
    public void onTweetClicked(Tweet tweet);
    public void onRetweet(Tweet tweet);
}
