package com.refiral.nomnom.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.refiral.nomnom.activity.HomeActivity;

public class MediaService extends Service {
    public static final String TAG = MediaService.class.getName();

    public MediaService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        Log.d(TAG, "onStartCommand action = " + action);

        if(CustomService.TAG.equals(action)) {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            MediaPlayer player = MediaPlayer.create(this, notification);
            player.setLooping(true);
            player.start();
            player.setVolume(1f, 1f);
        } else if(HomeActivity.TAG.equals(action)) {
            Log.d(TAG, "stopping now");
            stopSelf();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
