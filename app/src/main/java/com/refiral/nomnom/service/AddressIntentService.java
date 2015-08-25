package com.refiral.nomnom.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.maps.model.LatLng;
import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.util.LocationUtils;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class AddressIntentService extends IntentService {

    public AddressIntentService() {
        super("AddressIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if(getResources().getString(R.string.intent_filter_location).equalsIgnoreCase(intent.getAction())) {
                LatLng mLatLng = LocationUtils.getLocationFromAddress
                        (this, intent.getStringExtra(Constants.Keys.KEY_ADDRESS));
                Intent iLocationBroadcast = new Intent(getResources().getString(R.string.intent_filter_location));
                iLocationBroadcast.putExtra(Constants.Keys.KEY_LATITUDE, mLatLng.latitude);
                iLocationBroadcast.putExtra(Constants.Keys.KEY_LONGITUDE, mLatLng.longitude);
                LocalBroadcastManager.getInstance(this).sendBroadcast(iLocationBroadcast);
            }
        }
    }

}
