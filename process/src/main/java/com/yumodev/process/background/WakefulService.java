package com.yumodev.process.background;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

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
