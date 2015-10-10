package main.pojo;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.annotations.SerializedName;

public class Tweet implements Parcelable {


    @SerializedName("created_at")
    public String dateCreated;

    @SerializedName("id")
    public String id;

    @SerializedName("text")
    public String text;

    @SerializedName("in_reply_to_status_id")
    public String inReplyToStatusId;

    @SerializedName("in_reply_to_user_id")
    public String inReplyToUserId;

    @SerializedName("in_reply_to_screen_name")
    public String inReplyToScreenName;

    @SerializedName("user")
    public TwitterUser user;

    public Tweet() {

    }

    public Tweet(Parcel input) {
        dateCreated = input.readString();
        id = input.readString();
        text = input.readString();
        inReplyToStatusId = input.readString();
        inReplyToUserId = input.readString();
        inReplyToScreenName = input.readString();

        Bundle userArguments = input.readBundle();
        user = new TwitterUser();
        user.name = userArguments.getString("name");
        user.screenName = userArguments.getString("screenName");
        user.profileImageUrl = userArguments.getString("pathToImage");
    }

    @Override
    public String toString() {
        return text;
    }

    public long getDateCreatedTimestamp() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
        dateFormat.setLenient(false);
        try {
            final Date created = dateFormat.parse(dateCreated);
            return created.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dateCreated);
        dest.writeString(id);
        dest.writeString(text);
        dest.writeString(inReplyToStatusId);
        dest.writeString(inReplyToUserId);
        dest.writeString(inReplyToScreenName);

        final Bundle userArguments = new Bundle();
        userArguments.putString("name", user.name);
        userArguments.putString("profileImageUrl", user.profileImageUrl);
        userArguments.putString("screenName", user.screenName);
        dest.writeBundle(userArguments);
    }

}