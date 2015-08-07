package com.refiral.nomnom.config;

import android.content.Context;
import android.content.Intent;

import com.refiral.nomnom.activity.HomeActivity;
import com.refiral.nomnom.activity.LoginActivity;
import com.refiral.nomnom.activity.SOSActivity;

/**
 * Created by tanay on 7/8/15.
 */
public class Router {

    public static void startLoginActivity(Context context, String className) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.Keys.STARTER_CLASS,className);
        context.startActivity(intent);
    }

    public static void startHomeActivity(Context context, String className) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(Constants.Keys.STARTER_CLASS,className);
        context.startActivity(intent);
    }

    public static void startSOSActivity(Context context, String className) {
        Intent intent = new Intent(context, SOSActivity.class);
        intent.putExtra(Constants.Keys.STARTER_CLASS,className);
        context.startActivity(intent);
    }

}
