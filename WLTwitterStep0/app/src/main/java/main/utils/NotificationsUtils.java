package main.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import main.R;
import main.WLTwitterApplication;
import main.ui.activities.WLTwitterLoginActivity;

/**
 * Created by Thomas Fossati on 25/10/2015.
 */
public class NotificationsUtils {
    public static void displayNewTweetsNotification(int nbTweets, boolean vibrate, boolean playSound) {
        final Context context = WLTwitterApplication.getContext();

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(String.format(context.getString(R.string.notification_content), nbTweets))
                .setAutoCancel(true);

        final Intent newIntent = new Intent(context, WLTwitterLoginActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(WLTwitterLoginActivity.class);
        stackBuilder.addNextIntent(newIntent);
        final PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        final Notification notification = mBuilder.build();

        if(vibrate){
            notification.defaults = Notification.DEFAULT_VIBRATE;
        }
        if(playSound){
            notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(42,notification);
    }
}
