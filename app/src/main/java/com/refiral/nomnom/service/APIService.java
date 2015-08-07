package com.refiral.nomnom.service;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.model.APIInterface;

/**
 * Created by tanay on 7/8/15.
 */


public class APIService extends RetrofitGsonSpiceService {

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(APIInterface.class);
    }

    @Override
    protected String getServerUrl() {
        return Constants.Global.BASE_URL;
    }

    /**
     * Created by tanay on 7/8/15.
     */
    public static class GCMService extends GcmListenerService {

        @Override
        public void onMessageReceived(String from, Bundle data) {

        }
    }
}
