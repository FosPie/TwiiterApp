package main;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import main.helpers.TwitterHelper;
import main.interfaces.TweetChangeListener;
import main.pojo.Tweet;

/**
 * Created by thomas on 02/10/15.
 */
public class TwitterAsyncTask extends AsyncTask<String, Integer, List<Tweet>> {
    private TweetChangeListener listener;

    public TwitterAsyncTask(TweetChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Tweet> doInBackground(String... login) {
        if (!TextUtils.isEmpty(login[0])) {
            List<Tweet> tweetList = TwitterHelper.getTweetsOfUser(login[0]);
            return tweetList;
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Tweet> tweets) {
        super.onPostExecute(tweets);
        listener.onTweetRetrieved(tweets);
    }
}
