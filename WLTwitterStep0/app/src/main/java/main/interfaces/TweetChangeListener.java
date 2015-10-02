package main.interfaces;

import java.util.List;

import main.pojo.Tweet;

public interface TweetChangeListener {

	public void onTweetRetrieved(List<Tweet> tweets);
	
}
