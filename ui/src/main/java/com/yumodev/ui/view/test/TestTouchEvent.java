package com.yumodev.ui.view.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;

import com.yumodev.ui.recyclerview.Define;

/**
 * Created by yumodev on 17/12/19.
 */

public class TestTouchEvent extends LinearLayout {

    private final String LOG_TAG = "TestTouchEvent"+ Define.INSTANCE.getLOG_TAG();


    public TestTouchEvent(Context context) {
        super(context);
        setupUI();
    }

    public TestTouchEvent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public TestTouchEvent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupUI();
    }

    private void setupUI(){
        MyButton myButton = new MyButton(getContext());
        myButton.setText(getClass().getSimpleName());
        addView(myButton);

        setGravity(Gravity.CENTER);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(LOG_TAG, "dispatchTouchEvent:"+ev.getActionMasked());
        return super.dispatchTouchEvent(ev);
        //return true;
        //return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(LOG_TAG, "onInterceptTouchEvent:"+ev.getActionMasked());
        //return super.onInterceptTouchEvent(ev);
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                //return true;
                break;
            }
            case MotionEvent.ACTION_UP:{
                //return true;
                break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(LOG_TAG, "onTouchEvent:"+ev.getActionMasked());
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{

                break;
            }
            case MotionEvent.ACTION_MOVE:{
                break;
            }
            case MotionEvent.ACTION_UP:{
                break;
            }
        }
        return super.onTouchEvent(ev);
    }

    class MyButton extends android.support.v7.widget.AppCompatButton {

        private final String LOG_TAG = "Button:"+ Define.INSTANCE.getLOG_TAG();
        public MyButton(Context context) {
            super(context);
        }

        public MyButton(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            Log.i(LOG_TAG, "dispatchTouchEvent:"+ev.getActionMasked());
            switch (ev.getActionMasked()){
                case MotionEvent.ACTION_DOWN:{
                    break;
                }
                case MotionEvent.ACTION_MOVE:{
                    break;
                }
                case MotionEvent.ACTION_UP:{
                    break;
                }
            }
            return super.dispatchTouchEvent(ev);
            //return false;
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            Log.i(LOG_TAG, "onTouchEvent:"+ev.getActionMasked());
            switch (ev.getActionMasked()){
                case MotionEvent.ACTION_DOWN:{
                    break;
                }
                case MotionEvent.ACTION_MOVE:{
                    break;
                }
                case MotionEvent.ACTION_UP:{
                    break;
                }
            }
            //return super.onTouchEvent(ev);
            return true;
        }
    }


}
