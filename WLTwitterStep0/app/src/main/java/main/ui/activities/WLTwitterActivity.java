package main.ui.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;

import main.R;
import main.helpers.PreferencesHelper;
import main.interfaces.OnTweetSelectedListener;
import main.pojo.Tweet;
import main.receivers.NewTweetsReceiver;
import main.service.TweetService;
import main.ui.fragments.TweetFragment;
import main.ui.fragments.TweetsFragment;
import main.utils.Constants;

/**
 * Created by thomas on 25/09/15.
 */
public class WLTwitterActivity extends Activity implements OnTweetSelectedListener {

    private TweetsFragment tweetsFragment;
    private PendingIntent mServicePendingIntent;
    private NewTweetsReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String login = this.getIntent().getExtras().getString("login");
        this.getActionBar().setSubtitle(login);

        final Intent intent = new Intent(this, TweetService.class);
        this.startService(intent);

        this.tweetsFragment = new TweetsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.root, tweetsFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wltwitter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionLogout) {
            PreferencesHelper.deletePreferences();
            finish();

        }
        if (id == R.id.actionRefresh) {

            this.tweetsFragment.onRefresh();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTweetClicked(Tweet tweet) {

        Toast toast = Toast.makeText(this, tweet.text, Toast.LENGTH_SHORT);
        toast.show();
        final TweetFragment fragment = TweetFragment.newInstance(tweet);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack("single Tweet");
        transaction.replace(R.id.root, fragment);
        transaction.commit();


    }

    @Override
    protected void onResume() {
        super.onResume();

        final Calendar cal = Calendar.getInstance();

        final Intent intent = new Intent(this, TweetService.class);

        mServicePendingIntent = PendingIntent.getService(this, 0, intent, 0);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), Constants.Twitter.POLLING_DELAY, mServicePendingIntent);

        mReceiver = new NewTweetsReceiver();
        this.registerReceiver(mReceiver, new IntentFilter(Constants.General.ACTION_NEW_TWEETS));

    }

    @Override
    protected void onPause() {
        super.onPause();
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(mServicePendingIntent);
        unregisterReceiver(mReceiver);
        mReceiver = null;
    }

    @Override
    public void onRetweet(Tweet tweet) {
        Toast toast = Toast.makeText(this, "RT : " + tweet.text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
