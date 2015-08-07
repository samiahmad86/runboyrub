package com.refiral.nomnom.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.provider.Settings;

/**
 * Created by tanay on 7/8/15.
 */
public class DeviceUtils {

    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

}
