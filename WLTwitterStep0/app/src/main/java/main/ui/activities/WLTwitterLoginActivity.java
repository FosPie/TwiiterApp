package main.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import main.R;
import main.WLTwitterApplication;
import main.helpers.PreferencesHelper;

/**
 * Created by thomas on 25/09/15.
 */
public class WLTwitterLoginActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String login = PreferencesHelper.retriveLoginPreference();
        String pwd = PreferencesHelper.retrivePasswordPreference();
        findViewById(R.id.loginButton).setOnClickListener(this);
        if (!login.isEmpty()) {
            startHomeActivity(login,pwd);
        }
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

            PreferencesHelper.setLoginDataPreferences(login,pwd);
            startHomeActivity(login,pwd);
        }

    }
    public void startHomeActivity(String login, String pwd){
        Intent intent = new Intent(this, WLTwitterActivity.class);

        Bundle extras = new Bundle();
        extras.putString("login", login);
        extras.putString("pwd", pwd);
        intent.putExtras(extras);
        startActivity(intent);

    }
}