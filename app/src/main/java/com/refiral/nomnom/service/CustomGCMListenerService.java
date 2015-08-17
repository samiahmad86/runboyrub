package com.refiral.nomnom.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.util.PrefUtils;

/**
 * Created by tanay on 8/8/15.
 */
public class CustomGCMListenerService extends GcmListenerService {

    public static final String TAG = CustomGCMListenerService.class.getName();

    @Override
    public void onMessageReceived(String from, Bundle data) {
        if(PrefUtils.getAccessToken() != null) {
            long orderId = Long.parseLong(data.getString(Constants.Keys.KEY_ORDER_ID));
            String status = data.getString(Constants.Keys.KEY_STATUS);

            Log.d(TAG, "order id " + orderId + " status " + status);

            // if the order id sent from the server is not same as current order discard notification
            long storedOrderId = PrefUtils.getCurrentOrderID();
            if(storedOrderId != -1) {
                // the order id is different from the current order so ignore this push
                if(orderId != storedOrderId) {
                    Log.d(TAG, "stored id is not -1 and not equal to current id");
                    return;
                }
            }

            if(status != null && status.equalsIgnoreCase(Constants.Values.ORDER_STATUS_CANCELLED)) {
                Log.d(TAG, "order has been cancelled");
                PrefUtils.deleteOrder();
                PrefUtils.deleteCurrentOrderID();
                PrefUtils.setStatus(Constants.Values.STATUS_PLACEHOLDER);
                PrefUtils.orderIsInProgress(false);
                LocalBroadcastManager.getInstance(this).
                        sendBroadcast(new Intent(getResources().getString(R.string.intent_filter_order)));
                return;
            }

            if(status != null && status.equalsIgnoreCase(Constants.Values.ORDER_STATUS_NEW)
                    && storedOrderId == orderId && PrefUtils.getStatus() != Constants.Values.STATUS_PLACEHOLDER) {
                Log.d(TAG, "order is new, id is same status is not placeholder");
                return;
            }

            Log.d(CustomService.TAG, "Is order in progress");

            if(PrefUtils.isOrderInProgress()) {
                return;
            }

            Log.d(CustomService.TAG, "order is in progress set to false");

            Log.d(TAG, "order is new, id is same status is placeholder");
            Intent intent = new Intent(CustomGCMListenerService.this, CustomService.class);
            intent.setAction(CustomService.ACTION_ORDER);
            intent.putExtra(Constants.Keys.KEY_ORDER_ID, orderId);
            intent.putExtra(Constants.Keys.KEY_STATUS, status);
            startService(intent);
        }
    }
}
