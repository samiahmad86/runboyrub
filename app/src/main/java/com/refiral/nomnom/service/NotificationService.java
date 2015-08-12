package com.refiral.nomnom.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.refiral.nomnom.R;
import com.refiral.nomnom.activity.HomeActivity;
import com.refiral.nomnom.config.Constants;
import com.refiral.nomnom.util.AtomicUtils;

import java.util.HashMap;

public class NotificationService extends Service {
    public static final String TAG = NotificationService.class.getName();

    private Vibrator mVibrator;
    private static HashMap<Integer, MediaPlayer> hmMediaPlayer = new HashMap<>();


    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        LocalBroadcastManager.getInstance(this).registerReceiver(brMedia, new IntentFilter(getResources().getString(R.string.intent_filter_notification)));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            Log.d(TAG, "onStartCommand action = " + action);

            if (CustomService.TAG.equals(action)) {
                NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(NotificationService.this);
                notifBuilder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("New order").setContentText("Select this notification to stop the song.");

                // start the HomeActivity on notification click
                Intent activityIntent = new Intent(NotificationService.this, HomeActivity.class);
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                activityIntent.putExtra(Constants.Keys.STARTER_CLASS, TAG);
                activityIntent.putExtra(Constants.Keys.KEY_NOTIF_ID, startId);
                PendingIntent pi = PendingIntent.getActivity(NotificationService.this, AtomicUtils.getRequestCode(), activityIntent, 0);
                notifBuilder.setContentIntent(pi);
                notifBuilder.setOngoing(true);

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(startId, notifBuilder.build());

                // set the phone to vibrate
                mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                long pattern[] = {0, 100, 200, 300, 400};
                mVibrator.vibrate(pattern, 0);

                // set the alarm tone
                Uri alarmToneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                MediaPlayer player = MediaPlayer.create(this, alarmToneUri);
                player.setLooping(true);
                player.start();
                hmMediaPlayer.put(startId, player);
                Log.d(TAG, "playing, startId " + startId);
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(brMedia);
    }

    private BroadcastReceiver brMedia = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "stopping service");
            int id = intent.getIntExtra(Constants.Keys.KEY_NOTIF_ID, 1);
            hmMediaPlayer.get(id).stop();
            hmMediaPlayer.remove(id);
            mVibrator.cancel();
            NotificationService.this.stopSelf(id);
        }
    };
}
