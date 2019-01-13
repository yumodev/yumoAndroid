package com.yumo.common.log;

import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by yumo on 2018/5/14.
 * https://blog.csdn.net/zly921112/article/details/51867079
 */

public class YmCrashHelper implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler mOriginHandler = null;
    private Context mContext = null;

    private YmCrashHelper(){
        if (Thread.getDefaultUncaughtExceptionHandler() != this){
            mOriginHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    public static YmCrashHelper getInstance(){
        return SingletonHolder.mInstance;
    }

    protected static class SingletonHolder{
        private static final YmCrashHelper mInstance = new YmCrashHelper();
    }

    public void init(Context context){
        mContext = context;
    }



    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String error = writer.toString();
        saveLog(error);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        if (mOriginHandler != null){
            mOriginHandler.uncaughtException(t, e);
        }
    }

    private void saveLog(String error){
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogFileUtil.saveMessage(Log.TAG_CRASH_LOG, error, new LogFileInfo(Log.TAG_CRASH_LOG, Log.TAG_CRASH_LOG+".txt"));
            }
        }).start();
    }
}
