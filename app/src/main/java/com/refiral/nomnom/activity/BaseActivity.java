package com.refiral.nomnom.activity;

import android.support.v7.app.AppCompatActivity;

import com.octo.android.robospice.SpiceManager;
import com.refiral.nomnom.service.APIService;
import com.refiral.nomnom.util.PrefUtils;

/**
 * Created by tanay on 7/8/15.
 */
public class BaseActivity extends AppCompatActivity {

    private SpiceManager mSpiceManager = new SpiceManager(APIService.class);

    @Override
    protected void onStart() {
        mSpiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        mSpiceManager.shouldStop();
        super.onStop();
    }

    protected SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

    protected boolean isUserLoggedIn() {
        return PrefUtils.getAccessToken() != null;
    }


}
