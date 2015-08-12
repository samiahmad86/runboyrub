package com.refiral.nomnom.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.config.Router;
import com.refiral.nomnom.model.LoginResponse;
import com.refiral.nomnom.model.User;
import com.refiral.nomnom.request.LoginRequest;
import com.refiral.nomnom.util.DeviceUtils;
import com.refiral.nomnom.util.PrefUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState != null) {
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
                Log.d(TAG, "login button clicked");
                String phoneNumber = ((EditText) findViewById(R.id.et_login_number)).getText().toString();
                if (!phoneNumber.matches(Constants.Regex.NUMBER)) {
                    Toast.makeText(LoginActivity.this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
                    break;
                }
                registerUser(phoneNumber);
                break;
            }
        }
    }

    private void registerUser(String phoneNumber) {
        User user = new User(phoneNumber);
        LoginRequest mLoginRequest = new LoginRequest(user, DeviceUtils.getDeviceID(this), PrefUtils.getGcmToken(), "Android");
        Log.d(TAG, "creating the request");
        getSpiceManager().execute(mLoginRequest, new RequestListener<LoginResponse>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                // fuck this shit
                Log.d(TAG, "failed to login");
                Log.d(TAG, spiceException.getMessage());
                Log.d(TAG, spiceException.getLocalizedMessage());
            }

            @Override
            public void onRequestSuccess(LoginResponse loginResponse) {
                // store the access token
                PrefUtils.setAccessToken(loginResponse.authToken);
                Log.d(TAG, loginResponse.authToken);
                Router.startHomeActivity(LoginActivity.this, TAG);
                LoginActivity.this.finish();
            }
        });

    }

}
