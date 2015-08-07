package com.refiral.nomnom.activity;

import android.os.Bundle;

import com.refiral.nomnom.R;

/**
 * Created by tanay on 7/8/15.
 */
public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState != null) {
            return;
        }


    }
}
