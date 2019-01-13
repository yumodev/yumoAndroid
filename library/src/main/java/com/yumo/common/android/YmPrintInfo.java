package com.yumo.common.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by yumodev on 17/6/22.
 * 该类主要是打印与该APP和系统的所有信息。
 * 主要是为了了解
 */

public class YmPrintInfo {
    private static final String LOG_TAG = "YmPrintInfo";

    /**
     * 打印App信息
     */
    public static void printAppInfo(Context context){
        Log.i(LOG_TAG, "packageName:"+context.getPackageName());
        Log.i(LOG_TAG, "packageCodePath:"+context.getPackageCodePath());
        Log.i(LOG_TAG, "packageResourcePath:"+context.getPackageResourcePath());
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            Log.i(LOG_TAG, "application dataDir:"+applicationInfo.dataDir);
            Log.i(LOG_TAG, "application processName:"+applicationInfo.processName);
            Log.i(LOG_TAG, "application sourceDir:"+applicationInfo.sourceDir);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印系统信息
     */
    public static void printSystemInfo(){

    }

}
