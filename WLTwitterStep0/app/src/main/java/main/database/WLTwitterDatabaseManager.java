package main.database;

import java.util.List;

import main.WLTwitterApplication;
import main.pojo.Tweet;
import main.pojo.TwitterUser;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

public class WLTwitterDatabaseManager {

    public static Tweet tweetFromCursor(Cursor c) {
        if (null != c) {
            final Tweet tweet = new Tweet();
            tweet.user = new TwitterUser();

            // Retrieve the date created
            if (c.getColumnIndex(WLTwitterDatabaseContract.DATE_CREATED) >= 0) {
                tweet.dateCreated = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.DATE_CREATED));
            }

            // Retrieve the user name
            if (c.getColumnIndex(WLTwitterDatabaseContract.USER_NAME) >= 0) {
                tweet.user.name = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.USER_NAME));
            }

            // Retrieve the user alias
            if (c.getColumnIndex(WLTwitterDatabaseContract.USER_ALIAS) >= 0) {
                tweet.user.screenName = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.USER_ALIAS));
            }

            // Retrieve the user image url
            if (c.getColumnIndex(WLTwitterDatabaseContract.USER_IMAGE_URL) >= 0) {
                tweet.user.profileImageUrl = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.USER_IMAGE_URL));
            }

            // Retrieve the text of the tweet
            if (c.getColumnIndex(WLTwitterDatabaseContract.TEXT) >= 0) {
                tweet.text = c.getString(c.getColumnIndex(WLTwitterDatabaseContract.TEXT));
            }

            return tweet;
        }
        return null;
    }

    public static ContentValues tweetToContentValues(Tweet tweet) {
        final ContentValues values = new ContentValues();

        // Set the date created
        if (!TextUtils.isEmpty(tweet.dateCreated)) {
            values.put(WLTwitterDatabaseContract.DATE_CREATED, tweet.dateCreated);
        }

        // Set the date created as timestamp
        values.put(WLTwitterDatabaseContract.DATE_CREATED_TIMESTAMP, tweet.getDateCreatedTimestamp());

        // Set the user name
        if (!TextUtils.isEmpty(tweet.user.name)) {
            values.put(WLTwitterDatabaseContract.USER_NAME, tweet.user.name);
        }

        // Set the user alias
        if (!TextUtils.isEmpty(tweet.user.screenName)) {
            values.put(WLTwitterDatabaseContract.USER_ALIAS, tweet.user.screenName);
        }

        // Set the user image url
        if (!TextUtils.isEmpty(tweet.user.profileImageUrl)) {
            values.put(WLTwitterDatabaseContract.USER_IMAGE_URL, tweet.user.profileImageUrl);
        }

        // Set the text of the tweet
        if (!TextUtils.isEmpty(tweet.text)) {
            values.put(WLTwitterDatabaseContract.TEXT, tweet.text);
        }

        return values;
    }

    public static void testDatabase(List<Tweet> tweets) {
        // TODO Retrieve a writableDatabase from your database helper

        // TODO Then iterate over the list of tweets, and insert all tweets in database

        // TODO Finally, after inserting all tweets in database, query the database to retrieve all entries as cursor, and log
    }

    public static void testContentProvider(List<Tweet> tweets) {
        WLTwitterApplication.getContext().getContentResolver().query(WLTwitterDatabaseContract.TWEETS_URI, WLTwitterDatabaseContract.PROJECTION_FULL, null, null, null);
        WLTwitterApplication.getContext().getContentResolver().insert(WLTwitterDatabaseContract.TWEETS_URI, null);
        WLTwitterApplication.getContext().getContentResolver().update(WLTwitterDatabaseContract.TWEETS_URI, null, null, null);
        WLTwitterApplication.getContext().getContentResolver().delete(WLTwitterDatabaseContract.TWEETS_URI, null, null);

    }
    /*
    Dans testDatabase(), on faisait de l'insert des tweets "à la main"
Dans testProvider(), on fait un query, un insert, un update et un delete. Mais on ajoute rien pour l'instant. (Le 2è argument des fonctions, soit ContentValues est null)

Le query est déjà fait par le CursorLoader.
Le but maintenant, c'est de mettre le tweets en paramètres de l'insert, pour les ajouter à la base de données par notre ContentProvider. Il faudra donc implémenter la fonction insert dans le ContentProvider

De même, implémenter le delete et l'update.
Ensuite, il a dit d'insérer un faux tweet (insert), puis de modifier le texte du tweet (update).
Ensuite je sais pas, faites un deuxième tweet, et supprimez-le.

Surtout, laissez-bien les Log dans les fonctions query/update/insert/delete. Le prof va les chercher dans les logs.
Il va aussi regarder la database pour voir si elle est pas vide (avec son script adb dans le diapo)

Et normalement, insert/update/delete doivent pas se faire le thread principal. Mais on va voir comment faire la prochaine fois. Donc c'est "autorisé".
	 */

}
