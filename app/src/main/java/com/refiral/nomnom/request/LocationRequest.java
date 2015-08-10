package com.refiral.nomnom.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.refiral.nomnom.model.APIInterface;
import com.refiral.nomnom.model.DeliveryBoyLocation;
import com.refiral.nomnom.model.SimpleResponse;

/**
 * Created by tanay on 8/8/15.
 */
public class LocationRequest extends RetrofitSpiceRequest<SimpleResponse, APIInterface> {

    private String mAccessToken;
    private DeliveryBoyLocation mLocation;

    public LocationRequest(DeliveryBoyLocation location, String accessToken) {
        super(SimpleResponse.class, APIInterface.class);
        this.mAccessToken = accessToken;
        this.mLocation = location;
    }

    public SimpleResponse loadDataFromNetwork() {
        return getService().updateLocation(mAccessToken, mLocation);
    }

}
