package com.yumo.android.test.async;

import android.os.Bundle;

import com.yumo.demo.view.YmTestFragment;
import com.yumo.common.thread.YmHandlerThreadUtil;

/**
 * Created by yumo on 2/12/16.
 */
public class HandleThreadTest extends YmTestFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        YmHandlerThreadUtil.getInstance().start();
    }

    public void testHandlerThread(){
        YmHandlerThreadUtil.getInstance().post(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToastMessage("testHandlerThread");
                    }
                });
            }
        });

    }
}
