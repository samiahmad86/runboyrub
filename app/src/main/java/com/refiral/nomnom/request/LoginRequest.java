package com.refiral.nomnom.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.refiral.nomnom.model.APIInterface;
import com.refiral.nomnom.model.LoginResponse;
import com.refiral.nomnom.model.User;

/**
 * Created by tanay on 8/8/15.
 */
public class LoginRequest extends RetrofitSpiceRequest<LoginResponse, APIInterface> {

    private User mUser;
    private String mDeviceID;
    private String mPushID;
    private String mDeviceType;

    public LoginRequest(User user, String deviceId, String pushId, String deviceType) {
        super(LoginResponse.class, APIInterface.class);
        this.mUser = user;
        this.mDeviceID = deviceId;
        this.mPushID = pushId;
        this.mDeviceType = deviceType;
    }

    public LoginResponse loadDataFromNetwork() {
       return getService().login(mDeviceID, mDeviceType, mPushID, mUser);
    }

}
