package com.refiral.nomnom.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.refiral.nomnom.config.Constants;

/**
 * Created by tanay on 8/8/15.
 */
public class CustomGCMListenerService extends GcmListenerService {

    public static final String TAG = CustomGCMListenerService.class.getName();

    @Override
    public void onMessageReceived(String from, Bundle data) {
        int orderId = data.getInt(Constants.Keys.KEY_ORDER_ID, 1);
        Log.d(TAG, "order received");
        Log.d(TAG, from);
        Log.d(TAG, "" + orderId);
        Intent intent = new Intent(CustomGCMListenerService.this, CustomService.class);
        intent.setAction(CustomService.ACTION_ORDER);
        intent.putExtra(Constants.Keys.KEY_ORDER_ID, orderId);
        startService(intent);
    }
}
