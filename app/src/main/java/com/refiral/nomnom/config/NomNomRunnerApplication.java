package com.refiral.nomnom.config;

import android.app.Application;

import com.refiral.nomnom.util.PrefUtils;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by tanay on 7/8/15.
 */
public class NomNomRunnerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        PrefUtils.init(this);
    }
}
