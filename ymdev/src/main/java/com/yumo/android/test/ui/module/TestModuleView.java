package com.yumo.android.test.ui.module;

import com.yumo.demo.view.YmTestFragment;
import com.yumo.android.test.ui.module.stack.test.StackPage;
import com.yumo.android.test.ui.module.stack.test.StackViewPage;

/**
 * Created by yumodev on 18/1/10.
 */

public class TestModuleView extends YmTestFragment {

    public void testStackView(){
        showTestView(new StackPage(getContext()));
    }

    public void testStackView1(){
        showTestView(new StackViewPage(getContext()));
    }
}
