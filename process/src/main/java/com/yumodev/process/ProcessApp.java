package com.yumodev.process;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.ConsolePrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.yumo.common.android.YmContext;
import com.yumo.common.thread.YmProcessUtil;
import com.yumo.demo.utils.YmDemoUtil;
import com.yumodev.process.background.YmActivityLifecycleCallbacks;
import com.yumodev.process.receiver.ProcessReceiver;
import com.yumodev.process.util.UmengAnalytics;
import com.yumodev.xglib.XgLib;

/**
 * Created by yumodev on 18/3/21.
 */

public class ProcessApp extends Application {
    private final String LOG_TAG = Define.mDebugLog +" ProcessApp " + YmProcessUtil.getCurrentProcessName();
    @Override
    public void onCreate() {
        //Crasheye.init(this, "c0132ee0");
        super.onCreate();
        YmDemoUtil.setAppPackageName("com.yumodev.process");
        YmContext.setAppContext(this);


        Log.i(LOG_TAG, "onCreate：" +" "+ Process.myUid() + Process.myPid()+" "+ YmProcessUtil.isMainProcess(this));

        if (YmProcessUtil.isMainProcess(this)){
            registerActivityLifecycleCallbacks(new YmActivityLifecycleCallbacks());
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    Log.i(LOG_TAG, "onActivityCrated:"+activity.getClass().getName());
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    Log.i(LOG_TAG, "onActivityStarted:"+activity.getClass().getName());
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
                    Log.i(LOG_TAG, "onActivityStopped:"+activity.getClass().getName());
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    Log.i(LOG_TAG, "onActivitySaveInstanceState:"+activity.getClass().getName());
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    Log.i(LOG_TAG, "onActivityDestroyed:"+activity.getClass().getName());
                }
            });


           registerComponentCallbacks(new ComponentCallbacks2() {
               @Override
               public void onTrimMemory(int level) {

               }

               @Override
               public void onConfigurationChanged(Configuration newConfig) {

               }

               @Override
               public void onLowMemory() {

               }
           });

            initMainThread();


            CrashReport.initCrashReport(getApplicationContext(), "5d1a0417e0", true);
           //ProcessLib.getInstance().init(getApplicationContext());
//            Log.i(LOG_TAG, "CrashReport app appid "+CrashReport.getAppID());

            XgLib.init(this);

            Toast.makeText(getApplicationContext(), "Process Application", Toast.LENGTH_SHORT).show();

        }else{
            Log.i(LOG_TAG, "Bulgy remote process");
//            CrashReport.initCrashReport(getApplicationContext(), "ae7536f4bd", true);
//            CrashReport.setBuglyDbName("lib");
//            Log.i(LOG_TAG, "CrashReport remote appid "+CrashReport.getAppID());

        }

        initXLog();
    }

    public void initMainThread(){
        ProcessReceiver mReceiver = new ProcessReceiver();

        mReceiver.register(this);


        UmengAnalytics.getInstance().init(this);

    }

    /**
     * Android系统内存不够的情况下会回调。
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(LOG_TAG, "onLowMemory:"+YmProcessUtil.getCurrentProcessName());
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        String levelName = "";

        switch (level){
            case Application.TRIM_MEMORY_BACKGROUND:{
                //40 ,应用程序处于后台，该应用处于LRU缓存列表的最近绘制，但不会被清理掉。
                levelName = "TRIM_MEMORY_BACKGROUND";
                break;
            }
            case Application.TRIM_MEMORY_COMPLETE:{
                //80 内存严重不足，该应用程序处于LRU缓存列表的最边缘位置，应用程序随时都有被回收的风险。
                levelName = "TRIM_MEMORY_COMPLETE";
                break;
            }
            case Application.TRIM_MEMORY_MODERATE:{
                //60， 应用程序处于后台缓存，应用处于LRU缓存的中间位置，有可能会杀死。
                levelName = "TRIM_MEMORY_MODERATE";
                break;
            }
            case Application.TRIM_MEMORY_RUNNING_LOW:{
                //内存级别10：处于前台运行，当前内存非常低，必须释放自身占用一些内存，否则影响性能
                levelName = "TRIM_MEMORY_RUNNING_LOW";
                break;
            }
            case Application.TRIM_MEMORY_RUNNING_CRITICAL:{
                //内存级别 15，大部分其他后台程序已被杀死，请务必释放部分自身内存。
                levelName = "TRIM_MEMORY_RUNNING_CRITICAL";
                break;
            }
            case Application.TRIM_MEMORY_RUNNING_MODERATE:{
                //内存级别5：应用程序处于前台运行，不会被杀死，当前内存低，其他进程要被杀死。
                levelName = "TRIM_MEMORY_RUNNING_MODERATE";
                break;
            }
            case Application.TRIM_MEMORY_UI_HIDDEN:{
                //按Home键或者按多窗口键就会回调
                levelName = "TRIM_MEMORY_UI_HIDDEN";
                break;
            }
        }
        Log.i(LOG_TAG, "onTrimMemory:"+level+" "+levelName+" "+YmProcessUtil.getCurrentProcessName());
    }

    /**
     * 只会在仿真机中调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(LOG_TAG, "onTerminate:"+YmProcessUtil.getCurrentProcessName());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void initXLog(){
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL             // 指定日志级别，低于该级别的日志将不会被打印，默认为 LogLevel.ALL
                        : LogLevel.NONE)
                .tag("ymprocess")                                         // 指定 TAG，默认为 "X-LOG"
                //.t()                                                   // 允许打印线程信息，默认禁止
                //.st(2)                                                 // 允许打印深度为2的调用栈信息，默认禁止
                //.b()                                                   // 允许打印日志边框，默认禁止
                .build();

        Printer androidPrinter = new AndroidPrinter();             // 通过 android.util.Log 打印日志的打印器
        Printer consolePrinter = new ConsolePrinter();             // 通过 System.out 打印日志到控制台的打印器
        Printer filePrinter = new FilePrinter                      // 打印日志到文件的打印器
                .Builder("/sdcard/ymprocess/")                              // 指定保存日志文件的路径
                .fileNameGenerator(new DateFileNameGenerator())        // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
                .backupStrategy(new NeverBackupStrategy())                     // 指定日志平铺器，默认为 DefaultFlattener
                .build();

        XLog.init(config, androidPrinter, consolePrinter,
                filePrinter);

    }
}
