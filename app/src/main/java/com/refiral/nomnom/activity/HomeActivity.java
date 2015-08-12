package com.refiral.nomnom.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.config.Router;
import com.refiral.nomnom.fragment.CustomFragment;
import com.refiral.nomnom.listeners.FragmentInteractionListener;
import com.refiral.nomnom.model.SimpleResponse;
import com.refiral.nomnom.request.LogoutRequest;
import com.refiral.nomnom.service.CustomService;
import com.refiral.nomnom.service.NotificationService;
import com.refiral.nomnom.util.AlarmUtils;
import com.refiral.nomnom.util.PrefUtils;

/**
 * Created by tanay on 7/8/15.
 */
public class HomeActivity extends BaseActivity implements FragmentInteractionListener {

    private PendingIntent alarmIntent;
    public static final String TAG = HomeActivity.class.getName();
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Toolbar tb = (Toolbar) findViewById(R.id.tb_home);
        setSupportActionBar(tb);

        if (savedInstanceState != null) {
            return;
        }

        FragmentManager fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        Intent intent = getIntent();
        String previousClassName = intent.getStringExtra(Constants.Keys.STARTER_CLASS);
        if (previousClassName.equals(NotificationService.TAG)) {
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
            AlarmUtils.setRepeatingAlarm(getApplicationContext(), alarmIntent, 60000 /*Constants.Values.FIVE_MINUTES_IN_MILLIS*/);
        }

        onFragmentInteraction(PrefUtils.getStatus());

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
                        PendingIntent alarmIntent = PendingIntent.getService(getApplicationContext(), 0, iLocationService, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmUtils.cancelAlarm(getApplicationContext(), alarmIntent);
                        PrefUtils.deleteAccessToken();
                        PrefUtils.deleteGcmToken();
                        Router.startSplashActivity(HomeActivity.this, TAG);
                        HomeActivity.this.finish();
                    }
                });
                return true;
            }
            case R.id.action_call: {
                Router.startSOSActivity(HomeActivity.this, TAG);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int code) {
        ft.replace(R.id.fl_home, CustomFragment.newInstance(code), "" + code).commit();
    }
}
