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

    public static void deleteAccessToken() {
        mEditor.remove(Constants.Keys.KEY_ACCESS_TOKEN).commit();
    }

    public static void setGcmToken(String token) {
        mEditor.putString(Constants.Keys.KEY_PUSH_ID, token).commit();
    }

    public static String getGcmToken() {
        return mSharedPrefs.getString(Constants.Keys.KEY_PUSH_ID, null);
    }

    public static void deleteGcmToken() {
        mEditor.remove(Constants.Keys.KEY_PUSH_ID).commit();
    }

    public static void saveOrder(String orderJSON) {
        mEditor.putString(Constants.Keys.KEY_ORDER, orderJSON).commit();
    }

    public static String getOrder() {
        return mSharedPrefs.getString(Constants.Keys.KEY_ORDER, null);
    }

    public static void deleteOrder() {
        mEditor.remove(Constants.Keys.KEY_ORDER).commit();
    }

    public static void setStatus(int status) {
        mEditor.putInt(Constants.Keys.KEY_DELIVERY_BOY_STATUS, status).commit();
    }

    public static int getStatus() {
        return mSharedPrefs.getInt(Constants.Keys.KEY_DELIVERY_BOY_STATUS, Constants.Values.STATUS_PLACEHOLDER);
    }

    public static void setBillPhoto(String photo) {
        mEditor.putString(Constants.Keys.KEY_BILL_PHOTO, photo).commit();
    }

    public static String getBillPhoto() {
        return mSharedPrefs.getString(Constants.Keys.KEY_BILL_PHOTO, null);
    }

    public static void setCurrentOrderID(long id) {
        mEditor.putLong(Constants.Keys.KEY_ORDER_ID, id).commit();
    }
    public static long getCurrentOrderID() {
        return mSharedPrefs.getLong(Constants.Keys.KEY_ORDER_ID, -1);
    }

    public static void deleteCurrentOrderID() {
        mEditor.remove(Constants.Keys.KEY_ORDER_ID).commit();
    }
}
