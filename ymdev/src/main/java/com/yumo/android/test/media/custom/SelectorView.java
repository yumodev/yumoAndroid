/**
 * SelectorView.java
 * yumo
 * 2014-12-2
 * TODO
 */
package com.yumo.android.test.media.custom;

import java.util.ArrayList;
import java.util.List;

import com.yumo.android.R;
import com.yumo.android.test.media.canvas.TestShapeFragment;

import android.content.Context;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * yumo
 */
public class SelectorView extends LinearLayout {
    private final String TAG = TestShapeFragment.class.getSimpleName();

    /**
     * 存放shape资源。
     */
    private List<Integer> mResList = null;

    public SelectorView(Context context) {
        super(context);
        init();
    }

    public SelectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOrientation(LinearLayout.VERTICAL);
        initRes();
        initView();
    }

    private void initRes() {
        if (mResList == null) mResList = new ArrayList<Integer>();
        //边框圆角背景
        mResList.add(R.drawable.corners_bg);
        //渐变色
        mResList.add(R.drawable.shape_gradient);
        //描边
        mResList.add(R.drawable.shape_stoke);
        //圆形
        mResList.add(R.drawable.shape_type_oval);
    }

    private void initView() {
        for (Integer resId : mResList) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundResource(resId);
            LinearLayout.LayoutParams ltp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 40);
            ltp.topMargin = 10;
            ltp.leftMargin = 10;
            ltp.rightMargin = 10;
            addView(linearLayout, ltp);
        }
    }
}
