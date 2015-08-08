package com.refiral.nomnom.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.refiral.nomnom.model.APIInterface;
import com.refiral.nomnom.model.SimpleResponse;

/**
 * Created by tanay on 8/8/15.
 */
public class LogoutRequest extends RetrofitSpiceRequest<SimpleResponse, APIInterface> {

    private String accessToken;

    public LogoutRequest(String accessToken) {
        super(SimpleResponse.class, APIInterface.class);
        this.accessToken = accessToken;
    }

    public SimpleResponse loadDataFromNetwork() {
        return getService().logout(accessToken);
    }
}
