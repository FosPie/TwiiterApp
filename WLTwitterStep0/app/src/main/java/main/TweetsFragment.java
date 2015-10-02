package main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import main.interfaces.TweetChangeListener;
import main.pojo.Tweet;
import main.utils.Constants;
import main.utils.PreferenceUtils;


/**
 * Created by thomas on 02/10/15.
 */
public class TweetsFragment extends Fragment implements TweetChangeListener {

    private Activity activity;
    private ListView mListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wltwitter, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listTweets);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Context context = WLTwitterApplication.getContext();
        /*SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.login_data), Context.MODE_PRIVATE);
        final String login = sharedPreferences.getString("login","");
        if(!TextUtils.isEmpty(login)){
            TwitterAsyncTask task = new TwitterAsyncTask(this);
            task.execute(login);
        }*/

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onTweetRetrieved(List<Tweet> tweets) {
        for (Tweet t : tweets) Log.d("TweetAsyncTask", t.text);
        final ArrayAdapter<Tweet> adapter = new ArrayAdapter<Tweet>(getActivity(),R.layout.fragment_wltwitter,tweets);
        mListView.setAdapter(adapter);

    }
}
