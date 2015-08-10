package com.refiral.nomnom.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Router;
import com.refiral.nomnom.util.PrefUtils;

import java.io.IOException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * This class does the following functions
 *      1) Get the GCM ID and store it in SharedPreferences
 */
public class GCMRegisterIntentService extends IntentService {

    private static final String TAG = GCMRegisterIntentService.class.getName();


    public GCMRegisterIntentService() {
        super("CustomIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            if (Router.ACTION_GCM.equals(action)) {
                registerGCM();
            }
        }
    }

    /**
     * Get the GCM ID of the user and save it.
     */
    private void registerGCM() {
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.d(TAG, token);
            PrefUtils.setGcmToken(token);
            Router.startLoginActivity(GCMRegisterIntentService.this, GCMRegisterIntentService.class.getName(), Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
