package com.refiral.nomnom.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.refiral.nomnom.model.APIInterface;
import com.refiral.nomnom.model.Order;

/**
 * Created by tanay on 8/8/15.
 */
public class OrderRequest extends RetrofitSpiceRequest<Order, APIInterface> {

    private String mAccessToken;
    private long mOrderId;

    public OrderRequest(String accessToken, long orderId) {
        super(Order.class, APIInterface.class);
        this.mAccessToken = accessToken;
        this.mOrderId = orderId;
    }

    @Override
    public Order loadDataFromNetwork() throws Exception {
        return getService().getOrder(mAccessToken, mOrderId, System.currentTimeMillis());
    }
}
