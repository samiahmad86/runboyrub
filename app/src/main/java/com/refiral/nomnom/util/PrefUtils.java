package com.refiral.nomnom.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.refiral.nomnom.config.Constants;

/**
 * Created by tanay on 7/8/15.
 */
public class PrefUtils {

    private static SharedPreferences mSharedPrefs;
    private static SharedPreferences.Editor mEditor;

    public static void init(Context context) {
        mSharedPrefs = context.getSharedPreferences(Constants.Keys.PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPrefs.edit();
        mEditor.commit();
    }

    public static String getAccessToken() {
        return mSharedPrefs.getString(Constants.Keys.KEY_ACCESS_TOKEN, null);
    }

    public static void setAccessToken(String accessToken) {
        mEditor.putString(Constants.Keys.KEY_ACCESS_TOKEN, accessToken).commit();
    }
}
