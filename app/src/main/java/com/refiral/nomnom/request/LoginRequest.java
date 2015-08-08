package com.refiral.nomnom.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.refiral.nomnom.model.APIInterface;
import com.refiral.nomnom.model.LoginResponse;
import com.refiral.nomnom.model.SimpleResponse;

/**
 * Created by tanay on 8/8/15.
 */
public class LoginRequest extends RetrofitSpiceRequest<LoginResponse, APIInterface> {

    private String contactNumber;
    private String mDeviceID;
    private String mPushID;
    private String mDeviceType;

    public LoginRequest(String contactNumber, String deviceId, String pushId, String deviceType) {
        super(LoginResponse.class, APIInterface.class);
        this.contactNumber = contactNumber;
        this.mDeviceID = deviceId;
        this.mPushID = pushId;
        this.mDeviceType = deviceType;
    }

    public LoginResponse loadDataFromNetwork() {
       return getService().login(mDeviceID, mDeviceType, mPushID, contactNumber);
    }

}
