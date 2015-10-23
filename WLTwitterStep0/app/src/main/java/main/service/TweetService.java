package main.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

import main.interfaces.TweetChangeListener;
import main.pojo.Tweet;

/**
 * Created by thomas on 23/10/15.
 */
public class TweetService extends Service implements TweetChangeListener{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTweetRetrieved(List<Tweet> tweets) {

    }
}
