package com.yumo.android.test.plugin;

import android.app.Activity;
import android.util.Log;

import com.yumo.common.android.YmContext;
import com.yumo.common.android.YmPrintInfo;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/6/22.
 */

public class TestClassLoader extends YmTestFragment {
    private final String LOG_TAG = "TestClassLoader";
    /**
     * 打印常用类的加载的ClassLoader
     */
    public void testClassLoader(){
        Log.i(LOG_TAG, "current activity:"+getActivity().getClassLoader().toString());
        Log.i(LOG_TAG, "activity:"+ Activity.class.getClassLoader().toString());
        Log.i(LOG_TAG, "this:"+this.getClass().getClassLoader().toString());
        Log.i(LOG_TAG, "Context:"+getContext().getClass().getClassLoader().toString());
    }

    /**
     * 打印对插件开发有用的信息
     */
    public void testPrintInfo(){
        YmPrintInfo.printAppInfo(YmContext.getAppContext());
    }
}
