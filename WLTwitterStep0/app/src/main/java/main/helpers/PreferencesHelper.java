package main.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import main.R;
import main.WLTwitterApplication;

/**
 * Created by thomas on 07/10/15.
 */
public class PreferencesHelper {
    private static Context context = WLTwitterApplication.getContext();

    private static SharedPreferences sharedPreferences = WLTwitterApplication.getContext().getSharedPreferences(context.getString(R.string.login_data), Context.MODE_PRIVATE);

    public static String  retriveLoginPreference(){
        return sharedPreferences.getString("login", "");

    }
    public static String  retrivePasswordPreference(){
        return sharedPreferences.getString("pwd", "");
    }
    public static void deletePreferences(){
        sharedPreferences.edit().remove("login").apply();
        sharedPreferences.edit().remove("password").apply();
    }
    public static void setLoginDataPreferences(String login, String password){
        sharedPreferences.edit().putString("login", login).apply();
        sharedPreferences.edit().putString("pwd", password).apply();
    }

}
