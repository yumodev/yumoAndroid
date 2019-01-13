package com.yumo.android.test.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by trunx on 10/8/16.
 */

public class MyIntentService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
