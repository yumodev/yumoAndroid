package com.yumodev.flurry;

import android.app.Application;
import android.util.Log;

import com.flurry.android.FlurryAgent;

/**
 * Created by yumo on 2018/4/9.
 */

public class FlurryApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlurryAgent.Builder builder = new FlurryAgent.Builder();
        builder.withCaptureUncaughtExceptions(true);
        builder.withLogEnabled(true);
        builder.withLogLevel(Log.VERBOSE);
        builder.build(this, "VTJ2JVCSXSCPZHYRDYTR");

        FlurryUtil.onStartSession(this);
    }
}
