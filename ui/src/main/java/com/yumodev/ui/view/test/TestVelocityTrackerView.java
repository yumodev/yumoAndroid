package com.yumodev.ui.view.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.yumodev.ui.R;
import com.yumodev.ui.recyclerview.Define;

/**
 * Created by yumodev on 18/1/24.
 */

public class TestVelocityTrackerView extends LinearLayout{
    private final String LOG_TAG = Define.INSTANCE.getLOG_TAG();
    private ImageView mTouchView;
    private float mLastMotionY;
    private float mLastMotionX;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mActivePointerId;
    private int mTouchSlop;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private VelocityTracker mVelocityTracker;
    private OverScroller mScroller;
    private boolean mDraging = false;

    public TestVelocityTrackerView(Context context) {
        super(context);
        setupUI();
    }

    public TestVelocityTrackerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public TestVelocityTrackerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupUI();
    }

    private void setupUI() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        viewConfiguration.getScaledOverflingDistance();
        mScroller = new OverScroller(getContext());
        mTouchView = new ImageView(getContext());
        mTouchView.setImageResource(R.drawable.mzd);
        addView(mTouchView);
        mTouchView.setClickable(true);

        setGravity(Gravity.CENTER);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(LOG_TAG, "onInterceptTouchEvent:"+ev.getActionMasked()+" x:"+ev.getX()+" y:"+ev.getY()+" rawX"+ev.getRawX()+" rawY:"+ev.getRawY());
        //return super.onInterceptTouchEvent(ev);
        int x = (int)ev.getX();
        int y = (int)ev.getY();
        int rawX = (int)ev.getRawX();
        int rawY = (int)ev.getRawY();
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                mLastMotionX = mInitialMotionX = x;
                mLastMotionY = mInitialMotionY = y;
                mActivePointerId = ev.getPointerId(0);
                break;
            }
            case MotionEvent.ACTION_MOVE:{
//                if (mActivePointerId == MotionEvent.INVALID_POINTER_ID){
//                   break;
//                }
//                int activePointerIndex = ev.findPointerIndex(mActivePointerId);
//
//                mLastMotionX = ev.getX(activePointerIndex);
//                mLastMotionY = ev.getY(activePointerIndex);
                break;
            }
            case MotionEvent.ACTION_UP:{
                Log.i(LOG_TAG, "action_up");
                //return true;
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:{
                Log.i(LOG_TAG, " action_pointer_down"+ev.getActionIndex()+" "+ev.getPointerId(ev.getActionIndex())+" "+ev.getPointerCount());
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:{
                Log.i(LOG_TAG, " action_pointer_up"+ev.getActionIndex()+" "+ev.getPointerId(ev.getActionIndex())+" "+ev.getPointerCount());
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(LOG_TAG, "onTouchEvent:"+ev.getActionMasked()+" x:"+ev.getX()+" y:"+ev.getY()+" rawX"+ev.getRawX()+" rawY:"+ev.getRawY());
        int x = (int)ev.getX();
        int y = (int)ev.getY();
        int rawX = (int)ev.getRawX();
        int rawY = (int)ev.getRawY();
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                mLastMotionX = mInitialMotionX = x;
                mLastMotionY = mInitialMotionY = y;
                mActivePointerId = ev.getPointerId(ev.getActionIndex());

                mVelocityTracker = VelocityTracker.obtain();
                mVelocityTracker.addMovement(ev);
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                if (mActivePointerId == MotionEvent.INVALID_POINTER_ID){
                    break;
                }
                int activePointerIndex = ev.findPointerIndex(mActivePointerId);

                mLastMotionX = ev.getX(activePointerIndex);
                mLastMotionY = ev.getY(activePointerIndex);
                int deltaX = (int)(mLastMotionX - mInitialMotionX);
                int deltaY = (int)(mLastMotionY - mInitialMotionY);
                //mTouchView.setTranslationX();
                //mTouchView.setTranslationY(mLastMotionY - mInitialMotionY);

                scrollTo(-deltaX, -deltaY);

                mDraging = true;

                mVelocityTracker.addMovement(ev);

                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);

                float velocityX = mVelocityTracker.getXVelocity(mActivePointerId);
                float velocityY = mVelocityTracker.getYVelocity(mActivePointerId);

                Log.i(LOG_TAG, "velocity:x"+velocityX + " "+velocityY);
                break;
            }
            case MotionEvent.ACTION_UP:{
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                if (mDraging && Math.abs((int)mVelocityTracker.getYVelocity(mActivePointerId)) > mMinimumVelocity){
                    fling(-mVelocityTracker.getXVelocity(mActivePointerId), -mVelocityTracker.getYVelocity(mActivePointerId));
                }

                releaseVelocityTracker();
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:{
                Log.i(LOG_TAG, " action_pointer_down"+ev.getActionIndex()+" "+ev.getPointerId(ev.getActionIndex())+" "+ev.getPointerCount());
                final int index = ev.getActionIndex();
                mActivePointerId = ev.getPointerId(index);
                mLastMotionX = (int) ev.getX(index);
                mLastMotionY = (int) ev.getY(index);
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:{
                Log.i(LOG_TAG, " action_pointer_up"+ev.getActionIndex()+" "+ev.getPointerId(ev.getActionIndex())+" "+ev.getPointerCount());
                int pointerIndex = ev.getActionIndex();
                int pointerId = ev.getPointerId(pointerIndex);
                if (mActivePointerId == pointerId){
                    final int newPointerIndex = (pointerIndex == 0) ? 1 : 0;
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                    mLastMotionX = (int) ev.getX(newPointerIndex);
                    mLastMotionY = (int) ev.getY(newPointerIndex);
                }
                break;
            }
        }
        return true;
    }

    private void releaseVelocityTracker() {
        if(null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void fling(float velocityX, float velocityY){
        mScroller.fling(
                getScrollX(),
                getScrollY(),
                (int)velocityX,
                (int)velocityY,
                0,
                0,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            Log.i(LOG_TAG, "computeScrollOffset: x "+mScroller.getCurrX()+" y:"+mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
