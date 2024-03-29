package com.refiral.nomnom.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
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

public class LoginActivity extends BaseActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    public static final String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Toolbar tb = (Toolbar) findViewById(R.id.tb_login);
        setSupportActionBar(tb);
        findViewById(R.id.btn_login).setOnClickListener(this);
        ((EditText) findViewById(R.id.et_login_number)).setOnEditorActionListener(this);


        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("ProgressBar")) {
                if (savedInstanceState.getInt("ProgressBar") == View.VISIBLE) {
                    findViewById(R.id.pb_login).setVisibility(View.VISIBLE);
                    findViewById(R.id.btn_login).setEnabled(false);
                }
            }
            return;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outstate) {
        Log.d(TAG, "" + (findViewById(R.id.pb_login).getVisibility() == View.VISIBLE));
        outstate.putInt("ProgressBar", findViewById(R.id.pb_login).getVisibility());
        super.onSaveInstanceState(outstate);
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
        if (id == R.id.action_call) {
            Router.callNumber(LoginActivity.this,
                    getResources().getString(R.string.ph_no_1));
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
                registerUser(phoneNumber);
                break;
            }
        }
    }

    private void registerUser(String phoneNumber) {
        if (!phoneNumber.matches(Constants.Regex.NUMBER)) {
            Toast.makeText(LoginActivity.this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        final View button = findViewById(R.id.btn_login);
        button.setEnabled(false);
        final View pb = findViewById(R.id.pb_login);
        pb.setVisibility(View.VISIBLE);
        User user = new User(phoneNumber);
        LoginRequest mLoginRequest = new LoginRequest(user, DeviceUtils.getDeviceID(this), PrefUtils.getGcmToken(), "android");
        Log.d(TAG, "creating the request");
        getSpiceManager().execute(mLoginRequest, new RequestListener<LoginResponse>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                // fuck this shit
                String message = "Failed to login with device id " + DeviceUtils.getDeviceID(LoginActivity.this)
                        + " gcm id " + PrefUtils.getGcmToken() + " error : " + spiceException.getMessage() +
                        " localized message : " + spiceException.getLocalizedMessage();
                Crashlytics.getInstance().core.log(message);
                Log.d(TAG, message);
                button.setEnabled(true);
                pb.setVisibility(View.GONE);
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

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            registerUser(textView.getText().toString());
        }
        return false;
    }
}
