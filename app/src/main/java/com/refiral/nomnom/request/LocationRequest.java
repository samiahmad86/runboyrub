package com.refiral.nomnom.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.refiral.nomnom.model.APIInterface;
import com.refiral.nomnom.model.SimpleResponse;

/**
 * Created by tanay on 8/8/15.
 */
public class LocationRequest extends RetrofitSpiceRequest<SimpleResponse, APIInterface> {

    private String mAccessToken;
    private double latitude;
    private double longitude;

    public LocationRequest(double latitude, double longitude, String accessToken) {
        super(SimpleResponse.class, APIInterface.class);
        this.mAccessToken = accessToken;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public SimpleResponse loadDataFromNetwork() {
        return getService().updateLocation(mAccessToken, latitude, longitude);
    }

}
