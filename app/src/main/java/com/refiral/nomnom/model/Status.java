package com.refiral.nomnom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.refiral.nomnom.config.Constants;

/**
 * Created by tanay on 22/8/15.
 */
public class Status {
    @Expose
    @SerializedName(Constants.Keys.KEY_ORDER_ID)
    public long id;

    @Expose
    @SerializedName(Constants.Keys.KEY_DELIVERY_STATUS)
    public String status;

    public Status() {
        // default constructor required for class to be inherited
    }

    public Status(long id, String status) {
        this.id = id;
        this.status = status;
    }
}
