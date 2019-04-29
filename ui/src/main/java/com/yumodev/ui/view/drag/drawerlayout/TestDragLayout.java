package com.yumodev.ui.view.drag.drawerlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yumodev.ui.R;

/**
 * Created by yumodev on 18/1/30.
 */

public class TestDragLayout extends FrameLayout {
    public TestDragLayout(@NonNull Context context) {
        super(context);
        initView();
    }

    public TestDragLayout(@NonNull Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
    }

    public TestDragLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(){
        DragLayout dragLayout = new DragLayout(getContext());
        dragLayout.setBackgroundResource(R.color.red);

        addView(dragLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        FrameLayout rightView = new FrameLayout(getContext());
        rightView.setBackgroundResource(R.color.blue);
        int width = getContext().getResources().getDisplayMetrics().widthPixels * 2 / 3;
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        dragLayout.addView(rightView, lp);

        post(() -> dragLayout.close(false));

    }
}
