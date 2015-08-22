package com.refiral.nomnom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.refiral.nomnom.config.Constants;

/**
 * Created by tanay on 22/8/15.
 */
public class StatusDelivered extends Status {

    @Expose
    @SerializedName(Constants.Keys.KEY_PAYMENT_CASH)
    public String amountPaidInCash;

    @Expose
    @SerializedName(Constants.Keys.KEY_PAYMENT_CARD)
    public String amountPaidViaCard;

    public StatusDelivered(long id, String status, String amountPaidInCash, String amountPaidViaCard) {
        this.id = id;
        this.status = status;
        this.amountPaidInCash = amountPaidInCash;
        this.amountPaidViaCard = amountPaidViaCard;
    }
}
