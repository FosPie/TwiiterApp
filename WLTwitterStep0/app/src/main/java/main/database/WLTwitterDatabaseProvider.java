package main.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import main.helpers.WLTwitterDatabaseHelper;
import main.utils.Constants;

/**
 * Created by thomas on 16/10/15.
 */
public class WLTwitterDatabaseProvider extends ContentProvider {

    private WLTwitterDatabaseHelper mDBHelper;
    private UriMatcher mUriMatcher;

    public static final int TWEET_CORRECT_URI_CODE = 42;

    @Override
    public boolean onCreate() {
        mDBHelper = new WLTwitterDatabaseHelper(getContext());
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(WLTwitterDatabaseContract.CONTENT_PROVIDER_TWEETS_AUTHORITY, WLTwitterDatabaseContract.TABLE_TWEETS, TWEET_CORRECT_URI_CODE);
        return true;

    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.v(Constants.General.LOG_TAG, "QUERY");
        Cursor c = mDBHelper.getReadableDatabase().query(WLTwitterDatabaseContract.TABLE_TWEETS, projection, selection, selectionArgs, sortOrder, null, null);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        if (mUriMatcher.match(uri) == TWEET_CORRECT_URI_CODE) {
            return WLTwitterDatabaseContract.TWEETS_CONTENT_TYPE;
        }
        throw new IllegalArgumentException("Unknow URI " + uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i(Constants.General.LOG_TAG, "INSERT");

        long id = mDBHelper.getReadableDatabase().insert(WLTwitterDatabaseContract.TABLE_TWEETS, null, values);
        final Uri applicationUri = ContentUris.withAppendedId(uri, id);
        if (id > 0) {

            getContext().getContentResolver().notifyChange(applicationUri, null);
        }
        return applicationUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(Constants.General.LOG_TAG, "DELETE");
        int delInt = mDBHelper.getReadableDatabase().delete(WLTwitterDatabaseContract.TABLE_TWEETS, selection, selectionArgs);
        final Uri applicationUri = ContentUris.withAppendedId(uri, delInt);
        getContext().getContentResolver().notifyChange(applicationUri, null);

        return delInt;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.e(Constants.General.LOG_TAG, "UPDATE");
        int upInt = mDBHelper.getReadableDatabase().delete(WLTwitterDatabaseContract.TABLE_TWEETS, selection, selectionArgs);
        final Uri applicationUri = ContentUris.withAppendedId(uri, upInt);
        getContext().getContentResolver().notifyChange(applicationUri, null);

        return upInt;
    }
}
