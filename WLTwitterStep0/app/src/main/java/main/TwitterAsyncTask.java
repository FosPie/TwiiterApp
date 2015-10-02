package main;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.List;

import main.helpers.TwitterHelper;
import main.pojo.Tweet;

/**
 * Created by thomas on 02/10/15.
 */
public class TwitterAsyncTask extends AsyncTask<String, Integer, List<Tweet>> {

    @Override
    protected List<Tweet> doInBackground(String... login) {
        if (login != null) {
            List<Tweet> tweetList = TwitterHelper.getTweetsOfUser(login.toString());
            return tweetList;
        } else{
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Tweet> tweets) {
        super.onPostExecute(tweets);
        for(Tweet t : tweets){
            System.out.println("[toto]"+t.text);
        }
    }
}
