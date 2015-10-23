package main.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.squareup.picasso.Picasso;

import main.R;
import main.WLTwitterApplication;
import main.database.WLTwitterDatabaseManager;
import main.interfaces.OnTweetSelectedListener;
import main.pojo.Tweet;
import main.view.ViewHolder;

/**
 * Created by thomas on 23/10/15.
 */
public class TweetsCursorAdapter extends CursorAdapter implements View.OnClickListener{
    private OnTweetSelectedListener onTweetSelectedListener;
    public TweetsCursorAdapter(Context context, Cursor c, int flags, OnTweetSelectedListener onTweetSelectedListener) {
        super(context, c, flags);
        this.onTweetSelectedListener = onTweetSelectedListener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = LayoutInflater.from(context).inflate(R.layout.tweet_layout,null);
        final ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder holder = (ViewHolder) view.getTag();
        final Tweet tweet = WLTwitterDatabaseManager.tweetFromCursor(cursor);
        holder.getButton().setTag(tweet);
        holder.getButton().setOnClickListener(this);
        Picasso.with(WLTwitterApplication.getContext()).load(tweet.user.profileImageUrl).into(holder.getImage());

        holder.getName().setText(tweet.user.name);

        holder.getAlias().setText("@" + tweet.user.screenName);

        holder.getText().setText(tweet.text);
        holder.getButton().setTag(tweet);
        (holder.getName()).setText(tweet.user.name);
    }

    @Override
    public void onClick(View v) {
        onTweetSelectedListener.onRetweet((Tweet)v.getTag());
    }
}
