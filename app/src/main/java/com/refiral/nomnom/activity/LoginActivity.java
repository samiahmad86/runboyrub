package com.refiral.nomnom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Constants;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(savedInstanceState != null) {
            return;
        }

        findViewById(R.id.btn_login).setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_login: {
                String phoneNumber = ((EditText) findViewById(R.id.et_login_number)).getText().toString();
                if(!phoneNumber.matches(Constants.Regex.NUMBER)) {
                    Toast.makeText(LoginActivity.this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
                    break;
                }
                /* TODO:
                    Send phone number to server and on success start HomeActivity
                  */
                break;
            }
        }
    }
}
