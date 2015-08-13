package com.refiral.nomnom.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.refiral.nomnom.model.APIInterface;
import com.refiral.nomnom.model.SimpleResponse;

import retrofit.mime.TypedFile;

/**
 * Created by tanay on 8/8/15.
 */
public class StatusRequest extends RetrofitSpiceRequest<SimpleResponse, APIInterface> {

    private String mAccessToken;
    private int mOrderId;
    private String mDeliveryStatus;
    private String mAmountPaidInCash;
    private String mAmountPaidViaCard;
    private boolean isPaid;     // for the customer
    private TypedFile billPhoto; // photo of the bill taken when receiving the order from the Restaurant.
    private String totalAmt;

    public StatusRequest(String accessToken, int orderId, String deliveryStatus) {
        this(accessToken, orderId, deliveryStatus, null, null, false, null, null);
    }

    public StatusRequest(String accessToken, int orderId, String deliveryStatus, TypedFile billPhoto, String amount) {
        this(accessToken, orderId, deliveryStatus, null, null, false, billPhoto, amount);
    }



    public StatusRequest(String accessToken, int orderId, String deliveryStatus, String ammountPaidInCash, String ammountPaidViaCard, boolean isPaid, TypedFile billPhoto, String amount) {
        super(SimpleResponse.class, APIInterface.class);
        this.mAccessToken = accessToken;
        this.mOrderId = orderId;
        this.mDeliveryStatus = deliveryStatus;
        this.mAmountPaidViaCard = ammountPaidViaCard;
        this.mAmountPaidInCash = ammountPaidInCash;
        this.isPaid = isPaid;
        this.billPhoto = billPhoto;
        this.totalAmt = amount;
    }


    @Override
    public SimpleResponse loadDataFromNetwork() throws Exception {
        if(isPaid) {
            return getService().updateStatus(mAccessToken, mOrderId, mDeliveryStatus, mAmountPaidViaCard, mAmountPaidInCash);
        } else if(billPhoto == null) {
            return getService().updateStatus(mAccessToken, mOrderId, mDeliveryStatus);
        } else {
            return getService().updateStatus(mAccessToken, mOrderId, mDeliveryStatus, billPhoto, totalAmt);
        }
    }
}
