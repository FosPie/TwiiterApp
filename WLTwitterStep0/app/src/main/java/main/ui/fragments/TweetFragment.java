package main.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import main.R;
import main.interfaces.OnTweetSelectedListener;
import main.pojo.Tweet;

/**
 * Created by thomas on 09/10/15.
 */
public class TweetFragment extends Fragment{

    RelativeLayout relativeLayout;
    OnTweetSelectedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tweet_fragment, container, false);
        //relativeLayout = (RelativeLayout)  rootView.findViewById(R.id.tweet);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
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
    public static TweetFragment newInstance(Tweet tweet){
        final TweetFragment tweetFragment= new TweetFragment();
        final Bundle arguments = new Bundle();
        //arguments.putStringArrayList("tweet");
        return  tweetFragment;
    }
}
