package main.receivers;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import main.utils.Constants;
import main.utils.NotificationsUtils;

/**
 * Created by Thomas Fossati on 25/10/2015.
 */
public class NewTweetsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final int nbNewTweets = intent.getExtras().getInt(Constants.General.ACTION_NEW_TWEETS_EXTRA_NB_TWEETS);
        Log.d("BroadCast","BroadCast Received with " + String.valueOf(nbNewTweets) + " tweets");
        NotificationsUtils.displayNewTweetsNotification(nbNewTweets, true, true);
    }
}
