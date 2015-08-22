package com.refiral.nomnom.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.refiral.nomnom.model.APIInterface;
import com.refiral.nomnom.model.SimpleResponse;
import com.refiral.nomnom.model.Status;
import com.refiral.nomnom.model.StatusDelivered;
import com.refiral.nomnom.model.StatusPayRestaurant;

/**
 * Created by tanay on 8/8/15.
 */
public class StatusRequest extends RetrofitSpiceRequest<SimpleResponse, APIInterface> {

    private String mAccessToken;
    private long mOrderId;
    private String mDeliveryStatus;
    private String mAmountPaidInCash;
    private String mAmountPaidViaCard;
    private boolean isPaid;     // for the customer
    private String totalAmt;

    public StatusRequest(String accessToken, long orderId, String deliveryStatus) {
        this(accessToken, orderId, deliveryStatus, null, null, false,  null);
    }

    public StatusRequest(String accessToken, long orderId, String deliveryStatus, String amount) {
        this(accessToken, orderId, deliveryStatus, null, null, false, amount);
    }



    public StatusRequest(String accessToken, long orderId, String deliveryStatus, String amountPaidInCash,
                         String amountPaidViaCard, boolean isPaid, String amount) {
        super(SimpleResponse.class, APIInterface.class);
        this.mAccessToken = accessToken;
        this.mOrderId = orderId;
        this.mDeliveryStatus = deliveryStatus;
        this.mAmountPaidViaCard = amountPaidViaCard;
        this.mAmountPaidInCash = amountPaidInCash;
        this.isPaid = isPaid;
        this.totalAmt = amount;
    }


    @Override
    public SimpleResponse loadDataFromNetwork() throws Exception {
        if(isPaid) {
            return getService().updateStatus(mAccessToken, new StatusDelivered(mOrderId, mDeliveryStatus,
                    mAmountPaidViaCard, mAmountPaidInCash));
        } else if(totalAmt == null) {
            return getService().updateStatus(mAccessToken, new Status(mOrderId, mDeliveryStatus));
        } else {
            return getService().updateStatus(mAccessToken, new StatusPayRestaurant(mOrderId, mDeliveryStatus, totalAmt));
        }
    }
}
