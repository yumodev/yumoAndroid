package com.yumodev.ui.view.touch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class TouchTestActivity extends Activity {

    private final String TAG_ACTIVITY = "ym_touch_activity";
    private final String TAG_VIEWGROUP = "ym_touch_viewgroup";
    private final String TAG_VIEW = "ym_touch_view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TestViewGroup(this));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG_ACTIVITY, "dispatchTouchEvent:"+ev.getActionMasked());
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                Log.i(TAG_ACTIVITY, "dispatchTouchEvent: action_down");
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                Log.i(TAG_ACTIVITY, "dispatchTouchEvent: action_move");
                break;
            }
            case MotionEvent.ACTION_UP:{
                Log.i(TAG_ACTIVITY, "dispatchTouchEvent: action_up");
                break;
            }
            case MotionEvent.ACTION_CANCEL:{
                Log.i(TAG_ACTIVITY, "dispatchTouchEvent: action_cancel");
                break;
            }
        }
        return super.dispatchTouchEvent(ev);
        //return true;
        //return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG_ACTIVITY, "onTouchEvent:"+ev.getActionMasked());
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                //Log.i(TAG_ACTIVITY, "onTouchEvent: action_down");
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                //Log.i(TAG_ACTIVITY, "onTouchEvent: action_move");
                break;
            }
            case MotionEvent.ACTION_UP:{
                //Log.i(TAG_ACTIVITY, "onTouchEvent: action_up");
                break;
            }
            case MotionEvent.ACTION_CANCEL:{
                //Log.i(TAG_ACTIVITY, "onTouchEvent: action_cancel");
                break;
            }
        }
        return super.onTouchEvent(ev);
    }

    class TestViewGroup extends LinearLayout {
        public TestViewGroup(Context context) {
            super(context);
            setupUI();
        }

        public TestViewGroup(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            setupUI();
        }

        public TestViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
            Log.i(TAG_VIEWGROUP, "dispatchTouchEvent:"+ev.getActionMasked());
            switch (ev.getActionMasked()){
                case MotionEvent.ACTION_DOWN:{
                    //Log.i(TAG_VIEWGROUP, "dispatchTouchEvent: action_down");
                    break;
                }
                case MotionEvent.ACTION_MOVE:{
                    //Log.i(TAG_VIEWGROUP, "dispatchTouchEvent: action_move");
                    break;
                }
                case MotionEvent.ACTION_UP:{
                    //Log.i(TAG_VIEWGROUP, "dispatchTouchEvent: action_up");
                    break;
                }
                case MotionEvent.ACTION_CANCEL:{
                    //Log.i(TAG_VIEWGROUP, "dispatchTouchEvent: action_cancel");
                    break;
                }
            }
            return super.dispatchTouchEvent(ev);
            //return true;
            //return false;
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            Log.i(TAG_VIEWGROUP, "onInterceptTouchEvent:"+ev.getActionMasked());
            //return super.onInterceptTouchEvent(ev);
            switch (ev.getActionMasked()){
                case MotionEvent.ACTION_DOWN:{
                    //Log.i(TAG_VIEWGROUP, "onInterceptTouchEvent: action_down");
                    break;
                }
                case MotionEvent.ACTION_MOVE:{
                    //Log.i(TAG_VIEWGROUP, "onInterceptTouchEvent: action_move");
                    break;
                }
                case MotionEvent.ACTION_UP:{
                    //Log.i(TAG_VIEWGROUP, "onInterceptTouchEvent: action_up");
                    break;
                }
                case MotionEvent.ACTION_CANCEL:{
                    //Log.i(TAG_VIEWGROUP, "onInterceptTouchEvent: action_cancel");
                    break;
                }
            }
            return super.onInterceptTouchEvent(ev);
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            Log.i(TAG_VIEWGROUP, "onTouchEvent:"+ev.getActionMasked());
            switch (ev.getActionMasked()){
                case MotionEvent.ACTION_DOWN:{
                    //Log.i(TAG_VIEWGROUP, "onTouchEvent: action_down");
                    break;
                }
                case MotionEvent.ACTION_MOVE:{
                    //Log.i(TAG_VIEWGROUP, "onTouchEvent: action_move");
                    break;
                }
                case MotionEvent.ACTION_UP:{
                    //Log.i(TAG_VIEWGROUP, "onTouchEvent: action_up");
                    break;
                }
                case MotionEvent.ACTION_CANCEL:{
                    //Log.i(TAG_VIEWGROUP, "onTouchEvent: action_cancel");
                    break;
                }
            }
            return super.onTouchEvent(ev);
        }
    }


    class MyButton extends androidx.appcompat.widget.AppCompatButton {
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
            Log.i(TAG_VIEW, "dispatchTouchEvent:"+ev.getActionMasked());
            switch (ev.getActionMasked()){
                case MotionEvent.ACTION_DOWN:{
                    //Log.i(TAG_VIEW, "onTouchEvent: action_down");
                    break;
                }
                case MotionEvent.ACTION_MOVE:{
                    //Log.i(TAG_VIEW, "onTouchEvent: action_move");
                    break;
                }
                case MotionEvent.ACTION_UP:{
                    //Log.i(TAG_VIEW, "onTouchEvent: action_up");
                    break;
                }
                case MotionEvent.ACTION_CANCEL:{
                    //Log.i(TAG_VIEW, "onTouchEvent: action_cancel");
                    break;
                }
            }
            return super.dispatchTouchEvent(ev);
            //return false;
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            Log.i(TAG_VIEW, "onTouchEvent:"+ev.getActionMasked());
            switch (ev.getActionMasked()){
                case MotionEvent.ACTION_DOWN:{
                    //Log.i(TAG_VIEW, "onTouchEvent: action_down");
                    break;
                }
                case MotionEvent.ACTION_MOVE:{
                    //Log.i(TAG_VIEW, "onTouchEvent: action_move");
                    break;
                }
                case MotionEvent.ACTION_UP:{
                    //Log.i(TAG_VIEW, "onTouchEvent: action_up"+ev.getActionMasked());
                    break;
                }
                case MotionEvent.ACTION_CANCEL:{
                    //Log.i(TAG_VIEW, "onTouchEvent: action_up"+ev.getActionMasked());
                    break;
                }
            }
            //return super.onTouchEvent(ev);
            return true;
        }
    }

}
