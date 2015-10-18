package main.database;

import java.util.Date;
import java.util.List;

import main.WLTwitterApplication;
import main.helpers.WLTwitterDatabaseHelper;
import main.pojo.Tweet;
import main.pojo.TwitterUser;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

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
        final SQLiteOpenHelper sqLiteOpenHelper = new WLTwitterDatabaseHelper((WLTwitterApplication.getContext()));
        final SQLiteDatabase tweetsDatabase = sqLiteOpenHelper.getWritableDatabase();

        for (Tweet tweet : tweets) {
            final ContentValues contentValues = WLTwitterDatabaseManager.tweetToContentValues(tweet);
            tweetsDatabase.insert(WLTwitterDatabaseContract.TABLE_TWEETS, "", contentValues);
        }

        final Cursor cursor = tweetsDatabase.query(WLTwitterDatabaseContract.TABLE_TWEETS, WLTwitterDatabaseContract.PROJECTION_FULL, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Tweet tweet = WLTwitterDatabaseManager.tweetFromCursor(cursor);
            Log.d("testdatabase", tweet.user.name);
            Log.d("testdatabase", tweet.text);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
    }

    public static void testContentProvider(List<Tweet> tweets) {
        for(Tweet tweet : tweets){
            WLTwitterApplication.getContext().getContentResolver().query(WLTwitterDatabaseContract.TWEETS_URI, WLTwitterDatabaseContract.PROJECTION_FULL, null, null, null);
            WLTwitterApplication.getContext().getContentResolver().insert(WLTwitterDatabaseContract.TWEETS_URI,tweetToContentValues(tweet));


        }
        Tweet fakeTweet1 = new Tweet();
        Tweet fakeTweet2 = new Tweet();

        TwitterUser fakeUser1 = new TwitterUser();
        TwitterUser fakeUser2 = new TwitterUser();

        fakeUser1.profileImageUrl = "http://perdu.com";
        fakeUser1.name = "IAmAFakenName";
        fakeUser1.screenName = "IAmAFakeScreenName";
        fakeTweet1.user = fakeUser1;
        fakeTweet1.text = "fake text";
        fakeTweet1.dateCreated  = new Date().toString();

        fakeUser2.profileImageUrl = "http://google.com";
        fakeUser2.name = "I AM";
        fakeUser2.screenName = "YOUR FATHER";
        fakeTweet2.user = fakeUser1;
        fakeTweet2.text = "fake text 2";
        fakeTweet2.dateCreated  = new Date().toString();


        WLTwitterApplication.getContext().getContentResolver().insert(WLTwitterDatabaseContract.TWEETS_URI, tweetToContentValues(fakeTweet1));
        WLTwitterApplication.getContext().getContentResolver().insert(WLTwitterDatabaseContract.TWEETS_URI, tweetToContentValues(fakeTweet2));
         WLTwitterApplication.getContext().getContentResolver().update(WLTwitterDatabaseContract.TWEETS_URI, tweetToContentValues(fakeTweet2), null, null);
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
Donc dans testProvider, il faut faire une boucle "for" pour insérer tous les tweets, puis faire des tests d'update et delete sur deux faux tweets.
	 */

}
