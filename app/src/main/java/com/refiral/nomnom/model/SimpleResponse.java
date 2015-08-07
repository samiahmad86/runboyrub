package com.refiral.nomnom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tanay on 8/8/15.
 */
public class SimpleResponse {

    @Expose
    @SerializedName("success")
    public boolean success;
}
