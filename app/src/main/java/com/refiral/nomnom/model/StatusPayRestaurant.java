package com.refiral.nomnom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.request.StatusRequest;

/**
 * Created by tanay on 22/8/15.
 */
public class StatusPayRestaurant extends Status {

    @Expose
    @SerializedName(Constants.Keys.KEY_AMOUNT_PAID)
    public String amountPaid;

    public StatusPayRestaurant(long id, String status, String amountPaid) {
        this.id = id;
        this.status = status;
        this.amountPaid = amountPaid;
    }
}
