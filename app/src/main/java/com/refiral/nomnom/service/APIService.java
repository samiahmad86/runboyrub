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


    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(getServerUrl()).setConverter(getConverter()).setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader(Constants.Keys.KEY_DEVICE_ID, DeviceUtils.getDeviceID(getBaseContext()));
                String accessToken = PrefUtils.getAccessToken();
                if(accessToken != null) {
                    request.addHeader(Constants.Keys.KEY_ACCESS_TOKEN, accessToken);
                }
            }
        });
        return builder;
    }
}
