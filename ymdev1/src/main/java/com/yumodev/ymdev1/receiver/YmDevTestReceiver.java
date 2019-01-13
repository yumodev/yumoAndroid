package com.yumodev.ymdev1.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.yumo.common.android.YmContext;
import com.yumo.common.log.Log;
import com.yumodev.ymdev1.define.DebugLog;

/**
 * Created by yumodev on 17/11/6.
 */

public class YmDevTestReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        Toast.makeText(YmContext.getAppContext(), message, Toast.LENGTH_SHORT).show();
        Log.i(DebugLog.LOG_TAG, getClass().getSimpleName()+" onReceiver:"+message);
    }
}
