package com.refiral.nomnom.config;

import android.app.Application;

import com.refiral.nomnom.util.PrefUtils;

/**
 * Created by tanay on 7/8/15.
 */
public class NomNomRunnerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PrefUtils.init(this);
    }
}
