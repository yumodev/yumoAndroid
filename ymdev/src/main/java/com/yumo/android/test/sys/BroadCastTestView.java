package com.yumo.android.test.sys;

import android.content.Intent;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/11/6.
 */

public class BroadCastTestView extends YmTestFragment {

    public void testSendBroadCast(){
        Intent intent = new Intent("com.yumodev.ymdev_test_receiver");
        intent.putExtra("message","Message Test" );
        getActivity().sendBroadcast(intent);
    }
}
