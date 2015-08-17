package com.refiral.nomnom.util;

import android.content.Context;
import android.hardware.Camera;
import android.provider.Settings;

import java.io.File;
import java.util.List;

/**
 * Created by tanay on 7/8/15.
 */
public class DeviceUtils {

    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static Camera.Size getOptimalSize(List<Camera.Size> sizes, int width, int height) {
        if(sizes == null) {
            return null;
        }
        final double ASPECT_RATIO_TOLERANCE = 0.2;
        double targetRatio = ((double) width) / height;
        double minDiff = Double.MAX_VALUE;

        Camera.Size optimalSize = null;

        for (Camera.Size size : sizes) {
            double ratio = ((double) size.width) / size.height;
            if(Math.abs(targetRatio - ratio) > ASPECT_RATIO_TOLERANCE) {
                continue;
            }
            if(Math.abs(size.height - height) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - height);
            }
        }

        if(optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if(Math.abs(size.height - height) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - height);
                }
            }
        }
        return optimalSize;
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {}
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
