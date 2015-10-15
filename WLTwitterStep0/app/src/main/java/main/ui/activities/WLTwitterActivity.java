package main.ui.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import main.R;
import main.helpers.PreferencesHelper;
import main.interfaces.OnTweetSelectedListener;
import main.pojo.Tweet;
import main.ui.fragments.TweetFragment;
import main.ui.fragments.TweetsFragment;

/**
 * Created by thomas on 25/09/15.
 */
public class WLTwitterActivity extends Activity implements OnTweetSelectedListener {
    private TweetsFragment tweetsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String login = this.getIntent().getExtras().getString("login");
        this.getActionBar().setSubtitle(login);

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
        if (id == R.id.actionLogout){
            PreferencesHelper.deletePreferences();
            finish();

        }
        if (id == R.id.actionRefresh){

            this.tweetsFragment.onRefresh();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTweetClicked(Tweet tweet) {

        Toast toast = Toast.makeText(this, tweet.text,Toast.LENGTH_SHORT);
        toast.show();
        final TweetFragment fragment = TweetFragment.newInstance(tweet);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack("single Tweet");
        transaction.replace(R.id.root, fragment);
        transaction.commit();


    }


    @Override
    public void onRetweet(Tweet tweet) {
        Toast toast = Toast.makeText(this, "RT : " +tweet.text,Toast.LENGTH_SHORT);
        toast.show();
    }
}
