package main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by thomas on 25/09/15.
 */
public class WLTwitterActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String login = this.getIntent().getExtras().getString("login");
        this.getActionBar().setSubtitle(login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wltwitter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionLogout){
            Context context = getApplicationContext();
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.login_data), Context.MODE_PRIVATE);

            sharedPreferences.edit().remove("login").commit();
            sharedPreferences.edit().remove("password").commit();
            sharedPreferences.edit().apply();
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
