package com.refiral.nomnom.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import com.refiral.nomnom.service.CustomIntentService;

/**
 * Created by tanay on 7/8/15.
 */
public class AlarmUtils {

    public static final long FIVE_MINUTES = 5 * 60 * 1000;

    public static void setRepeatingAlarm(Context context, PendingIntent pendingIntent, long duration) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, FIVE_MINUTES, FIVE_MINUTES, pendingIntent);
    }

    public static void cancelAlarm(Context context, PendingIntent intent) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(intent);
    }

}
