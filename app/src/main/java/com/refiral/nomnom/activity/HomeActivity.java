package com.refiral.nomnom.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.config.Router;
import com.refiral.nomnom.fragment.CameraFragment;
import com.refiral.nomnom.fragment.CustomFragment;
import com.refiral.nomnom.listeners.FragmentInteractionListener;
import com.refiral.nomnom.model.SimpleResponse;
import com.refiral.nomnom.request.LogoutRequest;
import com.refiral.nomnom.service.CustomService;
import com.refiral.nomnom.service.NotificationService;
import com.refiral.nomnom.util.AlarmUtils;
import com.refiral.nomnom.util.DeviceUtils;
import com.refiral.nomnom.util.PrefUtils;

/**
 * Created by tanay on 7/8/15.
 */
public class HomeActivity extends BaseActivity implements FragmentInteractionListener {

    public static final String TAG = HomeActivity.class.getName();
    private FragmentTransaction ft;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Toolbar tb = (Toolbar) findViewById(R.id.tb_home);
        setSupportActionBar(tb);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        long orderId = PrefUtils.getCurrentOrderID();

        if (orderId != -1 && PrefUtils.getStatus() != Constants.Values.STATUS_PLACEHOLDER) {
            getSupportActionBar().setTitle("Order ID : " + orderId);
        }

        if (savedInstanceState != null) {
            return;
        }

        LocalBroadcastManager.getInstance(this).
                registerReceiver(brOrder, new IntentFilter(getResources().getString(R.string.intent_filter_order)));

        Intent intent = getIntent();
        String previousClassName = intent.getStringExtra(Constants.Keys.STARTER_CLASS);

        if (previousClassName.equals(NotificationService.TAG)) {
            Log.d(TAG, "Order ID : " + intent.getStringExtra(Constants.Keys.KEY_ORDER_ID));
            // stop the notification service
            Intent iNotificationStop = new Intent(getResources().getString(R.string.intent_filter_notification));
            int notifId = intent.getIntExtra(Constants.Keys.KEY_NOTIF_ID, 1);
            iNotificationStop.putExtra(Constants.Keys.KEY_NOTIF_ID, notifId);
            LocalBroadcastManager.getInstance(this).sendBroadcast(iNotificationStop);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(notifId);
            PrefUtils.setStatus(Constants.Values.STATUS_CONFIRMED);
        } else if (LoginActivity.TAG.equals(previousClassName)) {
            // trigger service to get location for the first time
            Intent iLocationService = new Intent(getApplicationContext(), CustomService.class);
            iLocationService.setAction(CustomService.ACTION_LOC);
            // create pending intent for location alarm
            PendingIntent alarmIntent = PendingIntent.getService(getApplicationContext(), 0, iLocationService, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmUtils.setRepeatingAlarm(getApplicationContext(), alarmIntent, Constants.Values.FIVE_MINUTES_IN_MILLIS);
        }

        int status = PrefUtils.getStatus();
        if (status == Constants.Values.STATUS_PICKUP_PAY
                || status == Constants.Values.STATUS_PICKUP_MATCH || status == Constants.Values.STATUS_PICKUP_CONFIRM_PHOTO) {
            status = Constants.Values.STATUS_PICKUP_MATCH;
        }
        onFragmentInteraction(status, null);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(brOrder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout: {
                if (PrefUtils.getStatus() == Constants.Values.STATUS_PLACEHOLDER) {
                    LogoutRequest lor = new LogoutRequest(PrefUtils.getAccessToken());
                    getSpiceManager().execute(lor, new RequestListener<SimpleResponse>() {
                        @Override
                        public void onRequestFailure(SpiceException spiceException) {

                        }

                        @Override
                        public void onRequestSuccess(SimpleResponse simpleResponse) {
                            // actions to do on successful logout
                            // trigger service to get location for the first time
                            Intent iLocationService = new Intent(getApplicationContext(), CustomService.class);
                            iLocationService.setAction(CustomService.ACTION_LOC);
                            // create pending intent for location alarm
                            Log.d(TAG, "deleting stuff");
                            PendingIntent alarmIntent = PendingIntent.getService(getApplicationContext(), 0, iLocationService, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmUtils.cancelAlarm(getApplicationContext(), alarmIntent);
                            PrefUtils.deleteAccessToken();
                            PrefUtils.deleteGcmToken();
                            PrefUtils.deleteCurrentOrderID();
                            PrefUtils.deleteOrder();
                            PrefUtils.orderIsInProgress(false);
                            PrefUtils.setStatus(Constants.Values.STATUS_PLACEHOLDER);
                            Router.startSplashActivity(HomeActivity.this, TAG);
                            DeviceUtils.deleteCache(HomeActivity.this);
                            HomeActivity.this.finish();
                        }
                    });
                } else {
                    Toast.makeText(HomeActivity.this, "You cannot logout while an order is in progress",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            case R.id.action_call: {
                Router.callNumber(HomeActivity.this, getResources().getString(R.string.ph_no_1));
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int code, Bundle extras) {
        ft = fm.beginTransaction();
        if (code == Constants.Values.STATUS_PICKUP_PHOTO) {
            ft.replace(R.id.fl_home, CameraFragment.newInstance(), "" + code)
                    .addToBackStack("" + code)
                    .commit();
        } else if (code == Constants.Values.STATUS_PICKUP_PAY ||
                code == Constants.Values.STATUS_PICKUP_CONFIRM_PHOTO) {
            ft.replace(R.id.fl_home, CustomFragment.newInstance(code), "" + code)
                    .addToBackStack("" + code)
                    .commit();
        } else if (code == Constants.Values.STATUS_REACHED_CUSTOMER_ADDRESS) {
            Log.d(TAG, "popping back stack");
            fm.popBackStack("" + Constants.Values.STATUS_PICKUP_PHOTO, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.replace(R.id.fl_home, CustomFragment.newInstance(code), "" + code)
                    .commit();
        } else {
            ft.replace(R.id.fl_home, CustomFragment.newInstance(code), "" + code)
                    .commit();
        }
    }


    // update the ui when the order is deleted
    private BroadcastReceiver brOrder = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getSupportActionBar().setTitle(R.string.app_name);
            onFragmentInteraction(Constants.Values.STATUS_PLACEHOLDER, null);
        }
    };
}
