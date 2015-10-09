package main;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.List;

import main.pojo.Tweet;

/**
 * Created by thomas on 09/10/15.
 */
public class TweetsAdapter extends BaseAdapter {

    private LayoutInflater inflater = LayoutInflater.from(WLTwitterApplication.getContext());

    private List<Tweet> tweets;

    public TweetsAdapter(List<Tweet> tweets){
        this.tweets = tweets;


    }

    @Override
    public int getCount() {
        return tweets.size();
    }

    @Override
    public Object getItem(int position) {
        return tweets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(null == convertView){
            convertView = inflater.inflate(R.layout.tweet_layout, null);

        }
        final Tweet tweet = (Tweet) getItem(position);

        final ImageView avatar = (ImageView) convertView.findViewById(R.id.imageAvi);
        //avatar.setImageURI(Uri.parse(tweet.user.profileImageUrl));

        final TextView username = (TextView) convertView.findViewById(R.id.username);
        username.setText(tweet.user.name);

        final TextView userAlias = (TextView) convertView.findViewById((R.id.login));
        userAlias.setText("@" + tweet.user.screenName);

        final TextView text = (TextView) convertView.findViewById(R.id.textTweet);
        text.setText(tweet.text);

        return convertView;
    }


    public View getUglyView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.tweet_layout, null);
        final Tweet tweet = (Tweet) getItem(position);

        final TextView username = (TextView) view.findViewById(R.id.username);
        username.setText(tweet.user.name);

        final TextView userAlias = (TextView) view.findViewById((R.id.login));
        userAlias.setText("@" + tweet.user.screenName);

        final TextView text = (TextView) view.findViewById(R.id.textTweet);
        text.setText(tweet.text);

        return view;
    }
}
