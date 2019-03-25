package com.yumo.android.test.view.flip;


import com.yumo.demo.view.YmTestFragment;

public class TestFlipView extends YmTestFragment {

    public void testShowFlipView(){
        showTestView(new CustomFlipView(getContext()));
    }


}
