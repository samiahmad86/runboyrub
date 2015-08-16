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
            int orderId = Integer.parseInt(data.getString(Constants.Keys.KEY_ORDER_ID));
            Log.d(CustomService.TAG, "order id " + orderId);
            String status = data.getString(Constants.Keys.KEY_STATUS);

            if(status != null && status.equalsIgnoreCase(Constants.Values.ORDER_STATUS_CANCELLED)) {
                PrefUtils.deleteOrder();
                PrefUtils.deleteCurrentOrderID();
                PrefUtils.setStatus(Constants.Values.STATUS_PLACEHOLDER);
                LocalBroadcastManager.getInstance(this).
                        sendBroadcast(new Intent(getResources().getString(R.string.intent_filter_order)));
                return;
            }

            Intent intent = new Intent(CustomGCMListenerService.this, CustomService.class);
            intent.setAction(CustomService.ACTION_ORDER);
            intent.putExtra(Constants.Keys.KEY_ORDER_ID, orderId);
            intent.putExtra(Constants.Keys.KEY_STATUS, status);
            Log.d(TAG, "starting service");
            startService(intent);
        }
    }
}
