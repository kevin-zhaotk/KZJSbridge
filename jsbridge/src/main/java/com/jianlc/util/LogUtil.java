package com.jianlc.util;

import android.util.Log;

public class LogUtil {

    private static boolean DEBUG = true;

    public static void d(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
        }
    }


    public static void i(String tag, String message) {
        if (DEBUG) {
            Log.i(tag, message);
        }
    }


    public static void w(String tag, String message) {
        if (DEBUG) {
            Log.w(tag, message);
        }
    }


    public static void e(String tag, String message) {

        Log.e(tag, message);

    }
}
