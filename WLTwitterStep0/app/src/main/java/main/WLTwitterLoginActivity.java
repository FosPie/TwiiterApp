package main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by thomas on 25/09/15.
 */
public class WLTwitterLoginActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.login_data), Context.MODE_PRIVATE);
        String login = sharedPreferences.getString("login", "");
        String pwd = sharedPreferences.getString("pwd", "");
        findViewById(R.id.loginButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.loginEditText)).getText())) {

            Toast.makeText(this, R.string.error_no_login, Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(((EditText) findViewById(R.id.passwordTextEdit)).getText())) {
            Toast.makeText(this, R.string.error_no_password, Toast.LENGTH_LONG).show();
        } else {
            String login = String.valueOf(((EditText) findViewById(R.id.loginEditText)).getText());
            String pwd = String.valueOf(((EditText) findViewById(R.id.passwordTextEdit)).getText());

            Context context = getApplicationContext();
            SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.login_data), Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("login", login);
            sharedPreferences.edit().putString("pwd", pwd);
            sharedPreferences.edit().apply();
            Intent intent = new Intent(this, WLTwitterActivity.class);

            Bundle extras = new Bundle();
            extras.putString("login", login);
            extras.putString("pwd", pwd);
            intent.putExtras(extras);
            startActivity(intent);
        }

    }
}