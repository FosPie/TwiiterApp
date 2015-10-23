package main.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import main.async.TwitterAsyncTask;
import main.database.WLTwitterDatabaseManager;
import main.helpers.PreferencesHelper;
import main.interfaces.TweetChangeListener;
import main.pojo.Tweet;
import main.utils.Constants;
import main.utils.PreferenceUtils;

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


        WLTwitterDatabaseManager.testContentProvider(tweets);
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new TwitterAsyncTask(this).execute(PreferencesHelper.retriveLoginPreference());
        return Service.START_NOT_STICKY;
    }
}
