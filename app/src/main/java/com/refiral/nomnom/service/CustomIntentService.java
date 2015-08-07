package com.refiral.nomnom.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Constants;

import java.io.IOException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * This class does the following functions
 * 1) Update the user location
 * 2) Register the GCM id at the server
 */
public class CustomIntentService extends IntentService {
    // The actions the intent service can perform
    private static final String ACTION_LOC = "com.refiral.nomnom.receiver.action.LOC";
    private static final String ACTION_GCM = "com.refiral.nomnom.receiver.action.GCM";
    private static final String ACTION_ORDER = "com.refiral.nomnom.receiver.action.ORDER";

    /**
     * Returns Pending Intent for alarm to check for location. If
     * the service is already performing a task this action will be queued.
     */


    public static PendingIntent getLocationPendingIntent(Context context, int flags) {
        Intent intent = new Intent(context, CustomIntentService.class);
        intent.setAction(ACTION_LOC);
        return PendingIntent.getService(context, 0, intent, flags);
    }

    /**
     * Starts this service to register GCM id at server. If
     * the service is already performing a task this action will be queued.
     */
    public static void registerGCM(Context context) {
        Intent intent = new Intent(context, CustomIntentService.class);
        intent.setAction(ACTION_GCM);
        context.startService(intent);
    }

    /**
        Start this service to get the order details from the server
     */
    public static void getOrder(Context context, int orderId) {
        Intent intent = new Intent(context, CustomIntentService.class);
        intent.setAction(ACTION_ORDER);
        intent.putExtra(Constants.Keys.KEY_ORDER_ID, orderId);
        context.startService(intent);
    }

    public CustomIntentService() {
        super("CustomIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_LOC.equals(action)) {
                sendLocation();
            } else if (ACTION_GCM.equals(action)) {
                registerGCM();
            } else if(ACTION_ORDER.equals(action)) {
                getOrderDetails(intent.getIntExtra(Constants.Keys.KEY_ORDER_ID, -1));
            }
        }
    }

    /**
     * Register the GCM ID of the user at the server.
     */
    private void registerGCM() {
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // TODO: Send the token to the server
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Send the location to the server.
     */
    private void sendLocation() {

        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // TODO: Send the location to the server

    }

    /*
        get the order details
     */
    private void getOrderDetails(int orderID) {
        // TODO: Send the order details to the server and  on success create notification to start the home activity
    }
}
