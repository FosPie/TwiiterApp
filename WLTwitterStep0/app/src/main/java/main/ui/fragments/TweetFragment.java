package main.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import main.R;
import main.WLTwitterApplication;
import main.interfaces.OnTweetSelectedListener;
import main.pojo.Tweet;

/**
 * Created by thomas on 09/10/15.
 */
public class TweetFragment extends Fragment implements View.OnClickListener{

    private Tweet tweet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tweet_fragment, container, false);
        tweet = getArguments().getParcelable("tweet");
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        View rootView = this.getView();

        ((TextView)rootView.findViewById(R.id.login)).setText("@" + tweet.user.screenName);
        ((TextView)rootView.findViewById(R.id.username)).setText(tweet.user.name);
        ((TextView)rootView.findViewById(R.id.textTweet)).setText(tweet.text);
        ImageView imageAvi = (ImageView) rootView.findViewById(R.id.imageAvi);
        Picasso.with(WLTwitterApplication.getContext()).load(tweet.user.profileImageUrl).into(imageAvi);

        Button buttonReply = (Button)rootView.findViewById(R.id.buttonReply);
        Button buttonRT = (Button)rootView.findViewById(R.id.buttonRT);
        Button buttonStar = (Button)rootView.findViewById(R.id.buttonStar);

        buttonReply.setOnClickListener(this);
        buttonRT.setOnClickListener(this);
        buttonStar.setOnClickListener(this);
    }


    public static TweetFragment newInstance(Tweet tweet){
        final TweetFragment tweetFragment= new TweetFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelable("tweet",tweet);
        tweetFragment.setArguments(arguments);
        return tweetFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonReply:
                Toast.makeText(getActivity(), "Reply to : " + tweet.text, Toast.LENGTH_SHORT).show();
            case R.id.buttonRT:
                Toast.makeText(getActivity(), "RT : " + tweet.text, Toast.LENGTH_SHORT).show();
            case R.id.buttonStar:
                Toast.makeText(getActivity(), "Star : " + tweet.text, Toast.LENGTH_SHORT).show();
        }

    }
}
