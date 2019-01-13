package com.yumo.android.test.view.view;

import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yumo.android.R;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/3/31.
 */

public class TestCustomView extends YmTestFragment {

    public void testView(){
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setBackgroundResource(R.color.red);


        LinearLayout linearLayout1 = new LinearLayout(getContext());
        linearLayout1.setBackgroundResource(R.color.red);


        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setBackgroundResource(R.color.black);

        linearLayout1.addView(linearLayout2, new LinearLayout.LayoutParams(50, 200));

        linearLayout.addView(linearLayout1, new LinearLayout.LayoutParams(100, 400));

        showTestView(linearLayout, new FrameLayout.LayoutParams(100, 100));

    }
}
