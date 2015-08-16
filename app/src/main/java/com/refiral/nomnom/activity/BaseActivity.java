package com.refiral.nomnom.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.octo.android.robospice.SpiceManager;
import com.refiral.nomnom.service.APIService;
import com.refiral.nomnom.util.PrefUtils;

/**
 * Created by tanay on 7/8/15.
 */
public class BaseActivity extends AppCompatActivity {

    private SpiceManager mSpiceManager = new SpiceManager(APIService.class);
    protected boolean isKeyboardShown = false;

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


    protected void showKeyboard() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        isKeyboardShown = true;
    }

    protected void hideKeyboard() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        isKeyboardShown = false;
    }

}
