package com.yumodev.ui.scroll;

import android.animation.AnimatorSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yumo.common.android.YmDisplayUtil;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.ui.scroll.testview.ScrollerLayout;
import com.yumodev.ui.scroll.testview.TestScrollView1;
import com.yumodev.ui.scroll.testview.TestScrollView2;
import com.yumodev.ui.scroll.testview.TestScrollView3;

/**
 * Created by yumodev on 17/12/19.
 */

public class TestScroll extends YmTestFragment {

    public void testScroll1(){
        showTestView(new TestScrollView1(getContext()));
    }

    public void testScrollerLayout(){
        ScrollerLayout scrollerLayout = new ScrollerLayout(getContext());
        int height = YmDisplayUtil.getScreenWidth(getContext());
        scrollerLayout.addView(createButtom(-1, height, "One", null));
        scrollerLayout.addView(createButtom(-1, height, "Two", null));
        scrollerLayout.addView(createButtom(-1, height, "Three", null));

        showTestView(scrollerLayout);
    }

    public void testScrollView2(){
        showTestView(new TestScrollView2(getContext()));
    }

    public void testScrollView3(){
        showTestView(new TestScrollView3(getContext()));
    }

    private Button createButtom(int width, int height, String text, View.OnClickListener listener){
        Button button = new Button(getContext());
        button.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        button.setText(text);
        button.setOnClickListener(listener);
        return button;
    }
}
