package com.refiral.nomnom.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;

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


    /**
     * Starts this service to send the location to the server. If
     * the service is already performing a task this action will be queued.
     */


    public static PendingIntent getLocationPendingIntent(Context context) {
        Intent intent = new Intent(context, CustomIntentService.class);
        intent.setAction(ACTION_LOC);
        return PendingIntent.getService(context, 0, intent, 0);
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
            }
        }
    }

    /**
     * Register the GCM ID of the user at the server.
     */
    private void registerGCM() {

    }

    /**
     * Send the location to the server.
     */
    private void sendLocation() {
    }
}
