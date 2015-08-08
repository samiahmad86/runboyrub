package com.refiral.nomnom.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.refiral.nomnom.model.APIInterface;
import com.refiral.nomnom.model.SimpleResponse;

/**
 * Created by tanay on 8/8/15.
 */
public class StatusRequest extends RetrofitSpiceRequest<SimpleResponse, APIInterface> {

    private String mAccessToken;
    private int mOrderId;
    private String mDeliveryStatus;
    private String mAmountPaidInCash;
    private String mAmountPaidViaCard;
    private boolean isPaying;

    public StatusRequest(String accessToken, int orderId, String deliveryStatus) {
        this(accessToken, orderId, deliveryStatus, null, null, false);
    }

    public StatusRequest(String accessToken, int orderId, String deliveryStatus, String ammountPaidInCash, String ammountPaidViaCard, boolean isPaying) {
        super(SimpleResponse.class, APIInterface.class);
        this.mAccessToken = accessToken;
        this.mOrderId = orderId;
        this.mDeliveryStatus = deliveryStatus;
        this.mAmountPaidViaCard = ammountPaidViaCard;
        this.mAmountPaidInCash = ammountPaidInCash;
        this.isPaying = isPaying;
    }


    @Override
    public SimpleResponse loadDataFromNetwork() throws Exception {
        if(isPaying) {
            return getService().updateStatus(mAccessToken, mOrderId, mDeliveryStatus, mAmountPaidViaCard, mAmountPaidInCash);
        } else {
            return getService().updateStatus(mAccessToken, mOrderId, mDeliveryStatus);
        }
    }
}
