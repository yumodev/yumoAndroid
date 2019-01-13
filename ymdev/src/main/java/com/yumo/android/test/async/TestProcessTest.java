package com.yumo.android.test.async;

import com.yumo.common.thread.YmProcessUtil;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 2018/8/14.
 */

public class TestProcessTest extends YmTestFragment {

    public void testShowCurrentProcessName(){
        showToastMessage(YmProcessUtil.getCurrentProcessName(this.getContext()));
    }
}
