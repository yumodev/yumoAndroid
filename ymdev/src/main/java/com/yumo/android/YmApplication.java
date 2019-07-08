package com.yumo.android;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.yumo.android.test.TestClass;
import com.yumo.common.android.YmContext;
import com.yumo.common.net.YmNetManager;
import com.yumo.common.thread.YmHandlerThreadUtil;
import com.yumo.common.android.YmPrefManager;
import com.yumo.common.log.Log;
import com.yumo.demo.listener.IGetPackageData;
import com.yumo.ui.util.YmToast;

public class YmApplication extends MultiDexApplication {
    private final String LOG_TAG = Log.LIB_TAG;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(Log.LIB_TAG, "onCreate");
        YmHandlerThreadUtil.getInstance().start();
        YmPrefManager.getInstance().initialize(getApplicationContext());
        YmContext.setAppContext(this);
        YmToast.init(this.getApplicationContext());

        YmNetManager.getInstance().registerReceiver(getApplicationContext());
        IGetPackageData testClass = new TestClass();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.i(Log.LIB_TAG, "attachBaseContext");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        YmNetManager.getInstance().unregisterReceiver(getApplicationContext());
    }
}
