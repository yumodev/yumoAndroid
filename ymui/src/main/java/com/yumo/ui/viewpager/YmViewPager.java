package com.yumo.ui.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class YmViewPager extends ViewPager {

    private boolean mIsScroll = true;

    public YmViewPager(Context context) {
        super(context);
    }

    public YmViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean isScroll){
        mIsScroll = isScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //return super.onInterceptTouchEvent(ev);
        if (mIsScroll){
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mIsScroll && super.onTouchEvent(ev);

    }
}
