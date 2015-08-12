package com.refiral.nomnom.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.refiral.nomnom.R;
import com.refiral.nomnom.activity.HomeActivity;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.model.DeliveryBoyLocation;
import com.refiral.nomnom.model.Order;
import com.refiral.nomnom.model.SimpleResponse;
import com.refiral.nomnom.request.LocationRequest;
import com.refiral.nomnom.request.OrderRequest;
import com.refiral.nomnom.util.AtomicUtils;
import com.refiral.nomnom.util.PrefUtils;

/*
    This service is responsible for :
        1) Updating the location of the runner.
        2) Getting the order from the server and then killing itself.
 */

public class CustomService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // The actions the intent service can perform
    public static final String ACTION_LOC = "com.refiral.nomnom.service.action.LOC";
    public static final String ACTION_ORDER = "com.refiral.nomnom.service.action.ORDER";

    public static final String TAG = CustomService.class.getName();

    private GoogleApiClient mGoogleApiClient;
    private SpiceManager spiceManager;

    public CustomService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        spiceManager = new SpiceManager(APIService.class);
        Log.d(TAG, "onCreate called");
        spiceManager.start(CustomService.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_LOC.equals(action)) {
                Log.d(TAG, "starting spice manager");
                Log.d(TAG, "location request");
                buildgoogleApiClientObject();
                return START_STICKY;
            } else if (ACTION_ORDER.equals(action)) {
                getOrder(intent.getIntExtra(Constants.Keys.KEY_ORDER_ID, -1));
                return START_STICKY;
            }
        }
        return START_NOT_STICKY;
    }


    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            DeliveryBoyLocation dbl = new DeliveryBoyLocation(lastLocation.getLatitude(), lastLocation.getLongitude());
            LocationRequest lr = new LocationRequest(dbl, PrefUtils.getAccessToken());
            Log.d(TAG, "executing request");
            spiceManager.execute(lr, new RequestListener<SimpleResponse>() {
                @Override
                public void onRequestFailure(SpiceException spiceException) {

                }

                @Override
                public void onRequestSuccess(SimpleResponse simpleResponse) {
                    Log.d(TAG, "" + simpleResponse.success);
                    CustomService.this.stopSelf();
                }
            });
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void buildgoogleApiClientObject() {
        mGoogleApiClient = new GoogleApiClient.Builder(CustomService.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void getOrder(int orderId) {
        final OrderRequest or = new OrderRequest(PrefUtils.getAccessToken(), orderId);
        spiceManager.execute(or, new RequestListener<Order>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(Order order) {
                Gson gson = new Gson();
                String orderJSON = gson.toJson(order);
                Log.d(TAG, orderJSON);
                PrefUtils.saveOrder(orderJSON);

                // start the service to build the notification
                Intent iNotificationService = new Intent(CustomService.this, NotificationService.class);
                iNotificationService.setAction(TAG);
                startService(iNotificationService);

                CustomService.this.stopSelf();
            }
        });
    }
}
