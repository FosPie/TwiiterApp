package main;

import android.app.Activity;
import android.content.Intent;
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
        findViewById(R.id.loginButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.loginEditText)).getText()) || TextUtils.isEmpty(((EditText) findViewById(R.id.passwordTextEdit)).getText())) {

            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        } else {
            String login = ((EditText) findViewById(R.id.loginEditText)).getText().toString();
            String pwd = ((EditText) findViewById(R.id.passwordTextEdit)).getText().toString();
            Intent intent = new Intent(this, WLTwitterLoginActivity.class);
            startActivity(intent);
            Bundle extras = new Bundle();
            extras.putString("login", login);
            extras.putString("pwd", pwd);
        }

    }
}