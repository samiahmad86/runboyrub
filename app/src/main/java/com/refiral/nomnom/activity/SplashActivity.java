package com.refiral.nomnom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Router;
import com.refiral.nomnom.service.CustomIntentService;
import com.refiral.nomnom.util.PrefUtils;

public class SplashActivity extends BaseActivity {

    public static final String TAG = SplashActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // if orientation change has trigged this then do not redraw the activity
        if(savedInstanceState != null) {
            return;
        }

        CustomIntentService.registerGCM(this);

        if(isUserLoggedIn()) {
            Router.startHomeActivity(SplashActivity.this, TAG);
        } else {
            Router.startLoginActivity(SplashActivity.this, TAG);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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


    //////////////////////// private methods ////////////////////////////

    private boolean isUserLoggedIn() {
        return PrefUtils.getAccessToken() != null;
    }
}
