package com.refiral.nomnom.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

/**
 * Created by tanay on 7/8/15.
 */
public class AlarmUtils {

    public static final long FIVE_MINUTES = 5 * 60 * 1000;
    public static final String TAG = AlarmUtils.class.getName();

    public static void setRepeatingAlarm(Context context, PendingIntent pendingIntent, long duration) {
        Log.d(TAG, "setting the alarm");
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, duration, duration, pendingIntent);
    }

    public static void cancelAlarm(Context context, PendingIntent intent) {
        Log.d(TAG, "cancelling alarm");
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(intent);
    }

}
