package com.yumodev.processlib;

import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by yumo on 2018/4/9.
 */

public class ProcessLib {
    private static final ProcessLib ourInstance = new ProcessLib();

    public static ProcessLib getInstance() {
        return ourInstance;
    }

    private ProcessLib() {
    }

    public void init(Context context){
        CrashReport.initCrashReport(context, "ae7536f4bd", true);
        CrashReport.setAppPackage(context, "com.yumodev.processlib");
    }

    public void createBug(){
        CrashReport.testJavaCrash();
    }
}
