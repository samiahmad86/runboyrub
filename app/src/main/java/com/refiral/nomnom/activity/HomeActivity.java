package com.refiral.nomnom.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.refiral.nomnom.R;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.model.SimpleResponse;
import com.refiral.nomnom.request.LogoutRequest;
import com.refiral.nomnom.service.CustomService;
import com.refiral.nomnom.service.MediaService;
import com.refiral.nomnom.util.AlarmUtils;
import com.refiral.nomnom.util.PrefUtils;

/**
 * Created by tanay on 7/8/15.
 */
public class HomeActivity extends BaseActivity {

    private PendingIntent alarmIntent;
    public static final String TAG = HomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState != null) {
            return;
        }

        String previousClassName = getIntent().getStringExtra(Constants.Keys.STARTER_CLASS);
        if (previousClassName.equals(CustomService.TAG)) {
            Log.d(TAG, "stopping media playback");
            // stop the service to play the song
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(getResources().getString(R.string.intent_filter_media)));
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(1);
        }

        // trigger service to get location for the first time
        Intent intent = new Intent(HomeActivity.this, CustomService.class);
        intent.setAction(CustomService.ACTION_LOC);
        // create pending intent for location alarm
        alarmIntent = PendingIntent.getService(HomeActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmUtils.cancelAlarm(HomeActivity.this, alarmIntent);
        AlarmUtils.setRepeatingAlarm(HomeActivity.this, alarmIntent, Constants.Values.FIVE_MINUTES_IN_MILLIS);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            LogoutRequest lor = new LogoutRequest(PrefUtils.getAccessToken());
            getSpiceManager().execute(lor, new RequestListener<SimpleResponse>() {
                @Override
                public void onRequestFailure(SpiceException spiceException) {

                }

                @Override
                public void onRequestSuccess(SimpleResponse simpleResponse) {
                    AlarmUtils.cancelAlarm(HomeActivity.this, alarmIntent);
                    PrefUtils.deleteAccessToken();
                    HomeActivity.this.finish();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
