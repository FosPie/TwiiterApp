package main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import main.interfaces.OnArticleSelectedListener;
import main.interfaces.TweetChangeListener;
import main.pojo.Tweet;


/**
 * Created by thomas on 02/10/15.
 */
public class TweetsFragment extends Fragment implements TweetChangeListener, AdapterView.OnItemClickListener{

    private Activity activity;
    private ListView mListView;
    private OnArticleSelectedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_wltwitter, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listTweets);

        final ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        mListView.setEmptyView(progressBar);
        ((ViewGroup) rootView).addView(progressBar);

        mListView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = WLTwitterApplication.getContext().getSharedPreferences(getString(R.string.login_data), Context.MODE_PRIVATE);
        final String login = sharedPreferences.getString("login", "");

        if (!TextUtils.isEmpty(login)) {
            new TwitterAsyncTask(this).execute(login);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnArticleSelectedListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onTweetRetrieved(List<Tweet> tweets) {
        for (Tweet t : tweets) Log.d("TweetAsyncTask", t.text);
        final ArrayAdapter<Tweet> adapter = new ArrayAdapter<Tweet>(getActivity(), android.R.layout.simple_list_item_1, tweets);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Tweet tweet = (Tweet)parent.getItemAtPosition(position);
        mListener.onTweetClicked(tweet);
    }
}
