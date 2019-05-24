package com.yumo.android.test.view.recyclerview.nestedscroll;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by yumodev on 17/12/15.
 * [](http://www.jianshu.com/p/aff5e82f0174)
 */

public class NestedLayout extends LinearLayout implements NestedScrollingParent{
    public NestedLayout(Context context) {
        super(context);
    }

    public NestedLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
