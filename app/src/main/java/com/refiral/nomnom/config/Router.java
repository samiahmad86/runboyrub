package com.refiral.nomnom.config;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.refiral.nomnom.activity.HomeActivity;
import com.refiral.nomnom.activity.LoginActivity;
import com.refiral.nomnom.activity.SOSActivity;
import com.refiral.nomnom.activity.SplashActivity;
import com.refiral.nomnom.service.CustomService;
import com.refiral.nomnom.service.GCMRegisterIntentService;

import java.net.URI;

/**
 * Created by tanay on 7/8/15.
 */
public class Router {

    public static final String ACTION_GCM = "com.refiral.nomnom.service.action.GCM";

    public static void startLoginActivity(Context context, String className, int flag) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.Keys.STARTER_CLASS, className);
        if (flag != -1) {
            intent.addFlags(flag);
        }
        context.startActivity(intent);
    }

    public static void startHomeActivity(Context context, String className) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(Constants.Keys.STARTER_CLASS, className);
        context.startActivity(intent);
    }

    public static void startSOSActivity(Context context, String className) {
        Intent intent = new Intent(context, SOSActivity.class);
        intent.putExtra(Constants.Keys.STARTER_CLASS, className);
        context.startActivity(intent);
    }

    public static void startLocationService(Context context, String className, long latitude, long longitude) {
        Intent intent = new Intent(context, CustomService.class);
        intent.putExtra(Constants.Keys.STARTER_CLASS, className);
        intent.putExtra(Constants.Keys.KEY_LATITUDE, latitude);
        intent.putExtra(Constants.Keys.KEY_LONGITUDE, longitude);
        context.startService(intent);
    }

    public static void callNumber(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    /**
     * Starts  {@link android.app.IntentService} {@link GCMRegisterIntentService} to register GCM id and save it. If
     * the service is already performing a task this action will be queued.
     */
    public static void registerGCM(Context context) {
        Intent intent = new Intent(context, GCMRegisterIntentService.class);
        intent.setAction(ACTION_GCM);
        context.startService(intent);
    }

    public static void startSplashActivity(Context context, String className) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.putExtra(Constants.Keys.STARTER_CLASS, className);
        context.startActivity(intent);
    }

    public static void startPhotoUpload(Context context, String billPhoto, long orderId) {
        Intent intent = new Intent(context, CustomService.class);
        intent.setAction(CustomService.ACTION_PHOTO);
        intent.putExtra(Constants.Keys.KEY_ORDER_ID, orderId);
        intent.putExtra(Constants.Keys.KEY_BILL_PHOTO, billPhoto);
        context.startService(intent);
    }
}
