package com.yumodev.ui.view.drag.test;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumodev.ui.R;
import com.yumodev.ui.recyclerview.Define;

/**
 * Created by yumodev on 18/1/29.
 * * [ 神奇的 ViewDragHelper，让你轻松定制拥有拖拽能力的 ViewGroup](http://blog.csdn.net/briblue/article/details/73730386)
 * 移动一张图片， 移动后返回
 */

public class DragTestView5 extends FrameLayout {
    private final String LOG_TAG = Define.INSTANCE.getLOG_TAG();
    private ImageView mDragView;
    private TextView mAutoBackView;

    //从侧边滑动View
    private TextView mEdgeDragView;

    private float mSourceX;
    private float mSourceY;



    private ViewDragHelper mDragHelper;
    // 是否可以划出屏幕边缘
    private boolean mShouldClamp = true;

    public DragTestView5(@NonNull Context context) {
        super(context);
        setupUI();
    }

    private void setupUI() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());

        addView();

        mDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                Log.i(LOG_TAG, child.getClass().getSimpleName()+"   "+pointerId);
                if (child == mDragView || child == mAutoBackView){
                    return true;
                }
                return false;
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                if (capturedChild == mAutoBackView){
                    mSourceX = capturedChild.getLeft();
                    mSourceY = capturedChild.getTop();
                }
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.i(LOG_TAG, "clampViewPositionHorizontal:"+left+" "+dx);
                if (mShouldClamp){
                    if (left > getWidth() - child.getMeasuredWidth()){
                        left = getWidth() - child.getMeasuredWidth();
                    }else if (left < 0){
                        left = 0;
                    }
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                Log.i(LOG_TAG, "clampViewPositionVertical"+top+" "+dy);
                if (mShouldClamp){
                    if (top > getHeight() - child.getMeasuredHeight()){
                        top = getHeight() - child.getMeasuredHeight();
                    }else if (top < 0){
                        top = 0;
                    }
                }
                return top;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                //super.onEdgeDragStarted(edgeFlags, pointerId);
                mDragHelper.captureChildView(mEdgeDragView, pointerId);
            }

            /**
             * 释放时回调
             * @param releasedChild
             * @param xvel
             * @param yvel
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (releasedChild == mAutoBackView){
                    mDragHelper.settleCapturedViewAt((int)mSourceX, (int)mSourceY);
                    invalidate();
                }

            }
        });

        //设置左边缘可以滑动
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
    }

    private void addView(){
        mDragView = new ImageView(getContext());
        mDragView.setImageResource(R.drawable.mzd);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        addView(mDragView, lp);
        mDragView.setClickable(false);

        mAutoBackView = new TextView(getContext());
        mAutoBackView.setText("自动返回");
        LayoutParams lp1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.gravity = Gravity.RIGHT;
        addView(mAutoBackView, lp1);
        mAutoBackView.setClickable(false);

        mEdgeDragView = new TextView(getContext());
        mEdgeDragView.setText("边缘滑动");

        LayoutParams lp2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.gravity = Gravity.LEFT;
        lp2.setMargins(500, 0,0,0);
        addView(mEdgeDragView, lp2);
        mEdgeDragView.setClickable(false);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // continueSetting,捕获的View是否还需要继续移动。
        if (mDragHelper != null && mDragHelper.continueSettling(true)){
            invalidate();
        }
    }
}
