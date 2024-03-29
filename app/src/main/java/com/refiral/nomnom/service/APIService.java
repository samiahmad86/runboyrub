package com.refiral.nomnom.service;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.model.APIInterface;
import com.refiral.nomnom.util.DeviceUtils;
import com.refiral.nomnom.util.PrefUtils;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

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
        return Constants.Urls.BASE_URL;
    }


}
