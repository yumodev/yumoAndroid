package com.yumo.common.thread;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/**
 * Created by yumodev on 2/12/16.
 *
 * 处理简短的异步处理的任务
 */
public class YmHandlerThreadUtil {
    private final String LOG_TAG = "YmHandlerThreadUtil";

    private static YmHandlerThreadUtil mInstance = null;

    private HandlerThread mHandlerThread = null;
    private Handler mHandler = null;


    public static YmHandlerThreadUtil getInstance(){
        if (mInstance == null){
            mInstance = new YmHandlerThreadUtil();
        }

        return mInstance;
    }

    public void start(){
        if (mHandlerThread == null){
            mHandlerThread = new HandlerThread(LOG_TAG);
            mHandlerThread.start();

            mHandler = new Handler(mHandlerThread.getLooper()){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                }
            };
        }
    }

    public void post(Runnable run){
        mHandler.post(run);
    }

    public void postAtFrontOfQueue(Runnable run){
        mHandler.postAtFrontOfQueue(run);
    }

    public void post(Runnable run, long delayMillis){
        mHandler.postDelayed(run, delayMillis);
    }


    public void runOnUIthread(Activity activity, Runnable run){
        if (activity != null){
            activity.runOnUiThread(run);
        }
    }
}
