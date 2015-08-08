package com.refiral.nomnom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tanay on 8/8/15.
 */
public class LoginResponse {

    @Expose
    @SerializedName("auth_token")
    public String accessToken;
}
