package com.refiral.nomnom.service;

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
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.refiral.nomnom.R;
import com.refiral.nomnom.activity.HomeActivity;

public class MediaService extends Service {
    public static final String TAG = MediaService.class.getName();

    private MediaPlayer player;

    public MediaService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        LocalBroadcastManager.getInstance(this).registerReceiver(brMedia, new IntentFilter(getResources().getString(R.string.intent_filter_media)));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        Log.d(TAG, "onStartCommand action = " + action);

        if(CustomService.TAG.equals(action)) {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, 50, 0);
            player = MediaPlayer.create(this, notification);
            player.setLooping(true);
            player.start();
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
            player.stop();
            MediaService.this.stopSelf();
        }
    };
}
