package com.yumodev.process.background;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by yumo on 2018/4/25.
 */

public class YmActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private final String LOG_TAG = "YmActivityLifecycle";
    private int mNum = 0;
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onActivityCrated:"+activity.getClass().getName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.i(LOG_TAG, "onActivityStarted:"+activity.getClass().getName());
        mNum++;
        if (mNum == 1){
            activity.sendBroadcast(new Intent("finish activity"));
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.i(LOG_TAG, "onActivityResumed:"+activity.getClass().getName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.i(LOG_TAG, "onActivityPaused:"+activity.getClass().getName());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        mNum--;
        if (mNum == 0){
            Intent it = new Intent(activity, OnePxActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //activity.startActivity(it);
        }
        Log.i(LOG_TAG, "onActivityStopped:"+activity.getClass().getName()+" "+mNum);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.i(LOG_TAG, "onActivitySaveInstanceState:"+activity.getClass().getName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.i(LOG_TAG, "onActivityDestroyed:"+activity.getClass().getName());
    }

    public boolean isBackground(){
        if (mNum == 0){
            return true;
        }
        return false;
    }
}
