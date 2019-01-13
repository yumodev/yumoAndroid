package com.yumodev.process.test;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 2018/4/8.
 */

public class BuglyTest extends YmTestFragment {
    public void testA(){
        showToastMessage("hello");
    }

    public void testBugly(){
        String test = null;
        showToastMessage(test.toString());
        showToastMessage(String.valueOf(2/0));
    }
}
