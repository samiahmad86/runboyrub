package com.refiral.nomnom.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.model.LoginResponse;
import com.refiral.nomnom.model.SimpleResponse;
import com.refiral.nomnom.request.LocationRequest;
import com.refiral.nomnom.request.LoginRequest;
import com.refiral.nomnom.util.AlarmUtils;
import com.refiral.nomnom.util.DeviceUtils;
import com.refiral.nomnom.util.PrefUtils;

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
    private static final String ACTION_LOC = "com.refiral.nomnom.service.action.LOC";
    private static final String ACTION_GCM = "com.refiral.nomnom.service.action.GCM";
    private static final String ACTION_ORDER = "com.refiral.nomnom.service.action.ORDER";
    private static final String ACTION_LOGIN = "com.refiral.nomnom.service.action.LOGIN";

    private SpiceManager mSpiceManager = new SpiceManager(APIService.class);

    private static final String TAG = CustomIntentService.class.getName();

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

    /*
        Start this service to login the user
     */
    public static void loginUser(Context context, String phoneNumber) {
        Intent intent = new Intent(context, CustomIntentService.class);
        intent.setAction(ACTION_LOGIN);
        intent.putExtra(Constants.Keys.KEY_CONTACT_NUMBER, phoneNumber);
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
            } else if(ACTION_LOGIN.equals(action)) {
                registerUser(intent.getStringExtra(Constants.Keys.KEY_CONTACT_NUMBER));
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSpiceManager.start(CustomIntentService.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSpiceManager.shouldStop();
    }

    /**
     * Log in the user
     */
    private void registerUser(String phoneNumber) {
        LoginRequest mLoginRequest = new LoginRequest(phoneNumber, DeviceUtils.getDeviceID(CustomIntentService.this), PrefUtils.getGcmToken(), "Android");
        mSpiceManager.execute(mLoginRequest, new RequestListener<LoginResponse>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                // fuck this shit
                Log.d(TAG, "failed to login");
            }

            @Override
            public void onRequestSuccess(LoginResponse loginResponse) {
                // store the access token
                PrefUtils.setAccessToken(loginResponse.accessToken);
                Log.d(TAG, loginResponse.accessToken);
                // once the user has logged in set alarm for location updates
                AlarmUtils.setRepeatingAlarm(CustomIntentService.this, getLocationPendingIntent(CustomIntentService.this, 0), AlarmUtils.FIVE_MINUTES);
            }
        });
    }

    /**
     * Get the GCM ID of the user and save it.
     */
    private void registerGCM() {
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            PrefUtils.setGcmToken(token);
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
        LocationRequest  lr = new LocationRequest(location.getLatitude(), location.getLongitude(), PrefUtils.getAccessToken());
        mSpiceManager.execute(lr, new RequestListener<SimpleResponse>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Log.d(TAG, "failed to send location");
            }

            @Override
            public void onRequestSuccess(SimpleResponse simpleResponse) {
                Log.d(TAG, "location updateded successfully");
            }
        });
    }

    /*
        get the order details
     */
    private void getOrderDetails(int orderID) {
        // TODO: Send the order details to the server and  on success create notification to start the home activity
    }
}
