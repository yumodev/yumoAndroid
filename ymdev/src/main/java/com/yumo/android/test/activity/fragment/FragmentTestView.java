package com.yumo.android.test.activity.fragment;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 3/30/16.
 */
public class FragmentTestView extends YmTestFragment {

    public void testReplace(){
        FragmentDemo fragmentOne = new FragmentDemo();
        final FragmentDemo fragmentTwo = new FragmentDemo();

        showFragment(fragmentOne);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                showFragment(fragmentTwo);
            }
        },2000);
    }
}
