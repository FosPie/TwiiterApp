package main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import main.interfaces.OnTweetSelectedListener;
import main.pojo.Tweet;
import main.view.ViewHolder;

/**
 * Created by thomas on 09/10/15.
 */
public class TweetsAdapter extends BaseAdapter implements View.OnClickListener{

    private LayoutInflater inflater = LayoutInflater.from(WLTwitterApplication.getContext());
    private OnTweetSelectedListener onTweetSelectedListener;
    private List<Tweet> tweets;

    public TweetsAdapter(List<Tweet> tweets, OnTweetSelectedListener onTweetSelectedListener) {
        this.tweets = tweets;
        this.onTweetSelectedListener = onTweetSelectedListener;


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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.tweet_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Tweet tweet = (Tweet) getItem(position);



        Picasso.with(WLTwitterApplication.getContext()).load(tweet.user.profileImageUrl).into(holder.getImage());

        holder.getName().setText(tweet.user.name);

        holder.getAlias().setText("@" + tweet.user.screenName);

        holder.getText().setText(tweet.text);
        holder.getButton().setTag(tweet);
        holder.getButton().setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        onTweetSelectedListener.onRetweet((Tweet)v.getTag());
    }
}
