package com.yumodev.ui.view.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yumodev.ui.R;
import com.yumodev.ui.recyclerview.Define;

/**
 * Created by yumodev on 18/1/24.
 */

public class TestMultitouchView extends LinearLayout{
    private final String LOG_TAG = Define.INSTANCE.getLOG_TAG();
    private ImageView mTouchView;
    private float mLastMotionY;
    private float mLastMotionX;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mActivePointerId;

    public TestMultitouchView(Context context) {
        super(context);
        setupUI();
    }

    public TestMultitouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public TestMultitouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupUI();
    }

    private void setupUI() {
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
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                if (mActivePointerId == MotionEvent.INVALID_POINTER_ID){
                    break;
                }
                int activePointerIndex = ev.findPointerIndex(mActivePointerId);

                mLastMotionX = ev.getX(activePointerIndex);
                mLastMotionY = ev.getY(activePointerIndex);

                mTouchView.setTranslationX(mLastMotionX - mInitialMotionX);
                mTouchView.setTranslationY(mLastMotionY - mInitialMotionY);
                break;
            }
            case MotionEvent.ACTION_UP:{
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
}
