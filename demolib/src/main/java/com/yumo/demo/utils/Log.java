package com.yumo.demo.utils;

public class Log {
    private static boolean mIsDebug = true;

    private Log() {
    }


    public static int v(String tag, String msg) {
        if (!mIsDebug){
           return 0;
        }
        return android.util.Log.v(tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (!mIsDebug){
            return 0;
        }
        return android.util.Log.v(tag, msg, tr);
    }


    public static int d(String tag, String msg) {
        if (!mIsDebug){
            return 0;
        }
        return android.util.Log.d(tag, msg);
    }


    public static int d(String tag, String msg, Throwable tr) {
        if (!mIsDebug){
            return 0;
        }
        return android.util.Log.d(tag, msg, tr);
    }

    public static int i(String tag, String msg) {
        if (!mIsDebug){
            return 0;
        }
        return android.util.Log.i(tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (!mIsDebug){
            return 0;
        }
        return android.util.Log.i(tag, msg, tr);
    }

    public static int w(String tag, String msg) {
        if (!mIsDebug){
            return 0;
        }
        return android.util.Log.w(tag, msg);
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (!mIsDebug){
            return 0;
        }
        return android.util.Log.w(tag, msg, tr);
    }


    public static int w(String tag, Throwable tr) {
        if (!mIsDebug){
            return 0;
        }
        return android.util.Log.w(tag, tr);
    }


    public static int e(String tag, String msg) {
        return android.util.Log.e(tag, msg);
    }


    public static int e(String tag, String msg, Throwable tr) {
        return android.util.Log.e(tag, msg, tr);
    }


    public static boolean isIsDebug() {
        return mIsDebug;
    }

    public static void setIsDebug(boolean isDebug) {
        Log.mIsDebug = isDebug;
    }
}
