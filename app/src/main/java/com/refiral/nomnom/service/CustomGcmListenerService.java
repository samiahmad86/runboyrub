package com.refiral.nomnom.service;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
import com.refiral.nomnom.config.Constants;

/**
 * Created by tanay on 8/8/15.
 */
public class CustomGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        int orderId = data.getInt(Constants.Keys.KEY_ORDER_ID, -1);
        CustomIntentService.getOrder(this, orderId);
    }
}
