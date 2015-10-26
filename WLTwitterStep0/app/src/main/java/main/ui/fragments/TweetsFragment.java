package main.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import main.R;
import main.async.TwitterAsyncTask;
import main.WLTwitterApplication;
import main.database.WLTwitterDatabaseContract;
import main.database.WLTwitterDatabaseManager;
import main.interfaces.OnTweetSelectedListener;
import main.interfaces.TweetChangeListener;
import main.pojo.Tweet;
import main.adapters.TweetsCursorAdapter;
import main.service.TweetService;


/**
 * Created by thomas on 02/10/15.
 */
public class TweetsFragment extends Fragment implements TweetChangeListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener , LoaderManager.LoaderCallbacks<Cursor>{

    private SwipeRefreshLayout rootView;
    private ListView mListView;
    private OnTweetSelectedListener mListener;
    private TweetsCursorAdapter tweetsCursorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_wltwitter, container, false);
        this.rootView = (SwipeRefreshLayout) rootView;
        mListView = (ListView) rootView.findViewById(R.id.listTweets);

        final ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        mListView.setEmptyView(progressBar);
        ((ViewGroup) rootView).addView(progressBar);

        mListView.setOnItemClickListener(this);
        this.rootView.setOnRefreshListener(this);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTweetSelectedListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement OnTweetSelectedListener");
        }
    }

    @Override
    public void onTweetRetrieved(List<Tweet> tweets) {
        //final ArrayAdapter<Tweet> adapter = new ArrayAdapter<Tweet>(getActivity(), android.R.layout.simple_list_item_1, tweets);
        //mListView.setAdapter(new TweetsAdapter(tweets, mListener));
        //rootView.setRefreshing(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //final Tweet tweet = (Tweet) parent.getItemAtPosition(position);
        Cursor c = (Cursor) tweetsCursorAdapter.getItem(position);
        final Tweet tweet = WLTwitterDatabaseManager.tweetFromCursor(c);
        mListener.onTweetClicked(tweet);
    }

    @Override
    public void onRefresh() {
        final Intent intent = new Intent(getActivity(), TweetService.class);
        getActivity().startService(intent);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final CursorLoader cursorLoader = new CursorLoader(WLTwitterApplication.getContext());
        cursorLoader.setUri(WLTwitterDatabaseContract.TWEETS_URI);
        cursorLoader.setProjection(WLTwitterDatabaseContract.PROJECTION_FULL);
        cursorLoader.setSelection(null);
        cursorLoader.setSelectionArgs(null);
        cursorLoader.setSortOrder(null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(null != data){
            /*while(data.moveToNext()){
                final Tweet tweet = WLTwitterDatabaseManager.tweetFromCursor(data);
                Log.d("TweetsFragment",tweet.text);
                Log.d("TweetsFragment",tweet.user.name);
            }
            if(!data.isClosed()){
                data.close();
            }*/
            tweetsCursorAdapter = new TweetsCursorAdapter(getActivity(),data, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER,mListener);
            mListView.setAdapter(tweetsCursorAdapter);
            rootView.setRefreshing(false);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {


    }
}
