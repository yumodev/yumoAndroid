package com.yumodev.ui.view.drag.drawerlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.yumodev.ui.recyclerview.Define;

/**
 * Created by yumodev on 18/1/30.
 */

public class DragLayout extends FrameLayout implements DragListener{
    private final String LOG_TAG = Define.INSTANCE.getLOG_TAG();

    private ViewDragHelper mDragHelper;
    private View mDragView = null;
    private int mMinFlingVelocity;
    public DragLayout(@NonNull Context context) {
        super(context);
        initDragHelper();
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDragHelper();
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDragHelper();
    }

    private void initDragHelper(){
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //return super.onInterceptTouchEvent(ev);
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void open(boolean animate) {

    }

    @Override
    public void close(boolean animate) {
        if (mDragView == null){
            mDragView = getChildAt(0);
        }

        if (animate){
            mDragHelper.smoothSlideViewTo(mDragView, getMeasuredWidth(), 0);
        }else{
            Log.i(LOG_TAG, "close:"+mDragView.getMeasuredWidth()+" "+getWidth());
            //mDragView.layout(getWidth(), 0, mDragView.getMeasuredWidth(), getHeight());
            mDragView.offsetLeftAndRight(getWidth());
        }
    }



    private class DragHelperCallback extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.i(LOG_TAG, "tryCaptureView");
            return child == getChildAt(0);
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            Log.i(LOG_TAG, "onViewCaptured");
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            //super.onEdgeDragStarted(edgeFlags, pointerId);
            Log.i(LOG_TAG, "onEdgeDragStarted");
            mDragView = getChildAt(0);
            mDragHelper.captureChildView(mDragView, pointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.i(LOG_TAG, "onViewRelease:"+xvel+" "+yvel+" "+mDragView.getX());

            int dx = getWidth() - (int)mDragView.getX();
            int dragWidth = releasedChild.getWidth();
            int left = 0;
            if (dx > dragWidth / 2 || xvel < 0){
                //open(true);
                left = getWidth() - dragWidth;
            }else{
                //close(true);
                left = getWidth();
            }
            mDragHelper.settleCapturedViewAt(left, releasedChild.getTop());
            invalidate();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //return super.clampViewPositionHorizontal(child, left, dx);
            Log.i(LOG_TAG, "left:"+left+" dx:"+dx);
            if (left < getWidth() - mDragView.getMeasuredWidth()){
                left = getWidth() - mDragView.getMeasuredWidth();
            }else if (left > getWidth()){
                left = getWidth();
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            Log.i(LOG_TAG, "changeView:"+changedView.getMeasuredWidth()+" left:"+left+" top:"+top);
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            Log.i(LOG_TAG, "onViewDragStateChanged:"+state);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return super.getViewHorizontalDragRange(child);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return super.getViewVerticalDragRange(child);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper != null && mDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    /**
     * 页面状态(滑动,打开,关闭)
     */
    public enum Status {
        Drag, Open, Close
    }
}

interface DragListener{
    void open(boolean animate);
    void close(boolean animate);
}


