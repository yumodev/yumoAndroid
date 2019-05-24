package com.yumodev.ui.recyclerview.DragRecyclerView;

import android.content.Context;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by yumodev on 17/4/10.
 * 一个最简单的GridDragView的实现，使用RecyclerView实现。
 */

public class LinearDragView extends FrameLayout {
    public LinearDragView(@NonNull Context context) {
        super(context);
    }

    public LinearDragView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearDragView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
