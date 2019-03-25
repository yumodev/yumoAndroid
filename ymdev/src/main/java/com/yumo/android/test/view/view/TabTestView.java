package com.yumo.android.test.view.view;

import com.yumo.android.test.view.layout1.Layout1Fragment;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumo on 4/2/16.
 */
public class TabTestView extends YmTestFragment {

    public void testTabFragmentWeiXin(){
        showFragment(new TabFragmentWeiXin());
    }

    public void testLayout1Fragment(){
        showFragment(new Layout1Fragment());
    }
}
