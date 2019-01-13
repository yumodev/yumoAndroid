package com.yumodev.process.background;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.elvishew.xlog.XLog;
import com.yumo.common.define.YmDefine;
import com.yumo.common.util.YmDateUtil;
import com.yumodev.process.Define;
import com.yumodev.process.background.ProcessWakefulBroadcastReceiver;
import com.yumodev.process.util.TestAppUtil;

/**
 * Created by yumo on 2018/4/10.
 */

public class WakefulService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WakefulService() {
        super("WakefulService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //XLog.i(YmDateUtil.getStrTime()+" WakefulService onHandlerIntent");
        TestAppUtil.openService(getApplicationContext(), LocalService.class.getName());
        ProcessWakefulBroadcastReceiver.completeWakefulIntent(intent);
    }
}
