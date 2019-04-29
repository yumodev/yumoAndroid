package com.yumodev.ui.recyclerview.hormenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.yumodev.ui.recyclerview.Define;

/**
 * Created by yumodev on 18/1/31.
 * 水平左右菜单
 */

public class ItemHorMenuLayout extends FrameLayout {
    private final String LOG_TAG = Define.INSTANCE.getLOG_TAG();
    private ViewDragHelper mDragHelper;
    private Direction mCurrentDirection;
    private Status mStatus;
    enum Direction{
        left,
        right
    }

    public enum Status {
        Open,
        Close,
    }

    public ItemHorMenuLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public ItemHorMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemHorMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mCurrentDirection = Direction.right;
        mDragHelper = ViewDragHelper.create(this, new HorDragMenuCallBack());
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //return super.onInterceptTouchEvent(ev);
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                break;
            }
            case MotionEvent.ACTION_UP:{
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                break;
            }
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //return super.onTouchEvent(event);
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                break;
            }
            case MotionEvent.ACTION_UP:{
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                break;
            }
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        mDragHelper.processTouchEvent(ev);
        return true;
    }

    private int getMenuWidth(){
        return getChildAt(0).getMeasuredWidth();
    }

    class HorDragMenuCallBack extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == getChildAt(1);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //return super.clampViewPositionHorizontal(child, left, dx);
            Log.i(LOG_TAG, "clampViewPositionHorizontal:"+left+"  "+dx);
            if (mCurrentDirection == Direction.right){
                if (left < 0 && -left > getMenuWidth()){
                    left = -getMenuWidth();
                }else if(left > 0 && left < getMeasuredWidth() - getMenuWidth()){
                    left = 0;
                }
            }else if (mCurrentDirection == Direction.left){
                if (left > getMenuWidth()){
                    left = getMenuWidth();
                }
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return child.getMeasuredWidth();
            //return 0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
           // return super.getViewVerticalDragRange(child);
            return child.getMeasuredHeight();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }
    }

}
