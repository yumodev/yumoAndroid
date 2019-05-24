package com.yumodev.process.background;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.yumo.common.log.Log;

/**
 * Created by yumo on 2018/5/4.
 */

public class OnePxActivity extends Activity {

    private final String LOG_TAG = "OnePxActivity";
    private BroadcastReceiver br;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                finish();
            }
        };
        registerReceiver(br, new IntentFilter("finish activity"));
        checkScreenOn("onCreate");
    }

    private void checkScreenOn(String methodName) {
        Log.d(LOG_TAG, "from call method: " + methodName);
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        Log.i(LOG_TAG, "isScreenOn: "+isScreenOn);
        if(isScreenOn){
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        Log.i(LOG_TAG, "===onDestroy===");
        try{
            unregisterReceiver(br);
        }catch (IllegalArgumentException e){
            Log.e(LOG_TAG,"receiver is not resisted: "+e);
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkScreenOn("onResume");
    }
}
