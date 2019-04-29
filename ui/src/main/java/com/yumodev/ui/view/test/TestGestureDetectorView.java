package com.yumodev.ui.view.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yumodev.ui.R;
import com.yumodev.ui.recyclerview.Define;

/**
 * Created by yumodev on 18/1/26.
 */

public class TestGestureDetectorView extends FrameLayout {
    private final String LOG_TAG = Define.INSTANCE.getLOG_TAG();
    private ImageView mTouchView;
    //定义手势监听器
    GestureDetector mGestureDetector;
    public TestGestureDetectorView(@NonNull Context context) {
        super(context);

        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            // 手指按下触发
            @Override
            public boolean onDown(MotionEvent e) {
                Log.i(LOG_TAG, "onDown");
                return false;
            }

            //当用户按下按键后100ms还没有松开或者移动就会会叫。
            @Override
            public void onShowPress(MotionEvent e) {
                Log.i(LOG_TAG, "onShowPress:"+e.toString());
            }

            /**
             * 手指离开屏幕的时候，还没有触发onScroll和onLongPress回调。
             * @param e
             * @return
             */
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.i(LOG_TAG, "onSingeTapUp:"+e.toString());
                return false;
            }

            /**
             * 手指滚动的时候触发
             * @param e1
             * @param e2
             * @param distanceX
             * @param distanceY
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.i(LOG_TAG, "onScroll:"+distanceX+" "+distanceY);
                return false;
            }

            //在屏幕上长按触发，触发之后不会再触发其他回调，知道松开UP
            @Override
            public void onLongPress(MotionEvent e) {
                Log.i(LOG_TAG, "onLongPress");
            }

            //滑动
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i(LOG_TAG, "onFling");
                return false;
            }

            /**
             * 双击
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.i(LOG_TAG, "onDoubleTap");
                return super.onDoubleTap(e);
            }

            /**
             * 双击事件回调
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                Log.i(LOG_TAG, "onDoubleTapEvent");
                return super.onDoubleTapEvent(e);
            }

            @Override
            public boolean onContextClick(MotionEvent e) {
                Log.i(LOG_TAG, "onContextClick");
                return super.onContextClick(e);
            }

            /**
             * 单击确认
             * @param e
             * @return
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.i(LOG_TAG, "onSingleTapConfirmed");
                return super.onSingleTapConfirmed(e);
            }
        });

        setupUI();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return mGestureDetector.onTouchEvent(event);
//    }

    private void setupUI() {
        mTouchView = new ImageView(getContext());
        mTouchView.setImageResource(R.drawable.mzd);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        addView(mTouchView, lp);
        mTouchView.setClickable(true);

        mTouchView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }
}
