package com.jaycorp.utils;

import android.util.Log;

/**
 *<h1>Logging shorthand class</h1>
 *
 * Simple shortcut to allow Log usage with only on parameter. Replacing the TAG parameter with +LOG by default.
 *
 * Java usage:
 * @code Logs.i("text to display in LogCat");
 *
 * @author Jason Kisch (jckisch@gmail.com)
 * @version 1.0
 *
 */

public class Logs {

    public static String TAG = "+LOG";

    public static void i(String str) { Log.i(TAG, str); }
    public static void d(String str) { Log.d(TAG, str); }
    public static void e(String str) { Log.e(TAG, str); }
    public static void w(String str) { Log.w(TAG, str); }
    public static void v(String str) { Log.v(TAG, str); }
    public static void wtf(String str) {
        Log.wtf(TAG, str);
    }
}
