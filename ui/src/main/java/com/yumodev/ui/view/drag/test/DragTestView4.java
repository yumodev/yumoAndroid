package com.yumodev.ui.view.drag.test;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yumodev.ui.R;
import com.yumodev.ui.recyclerview.Define;

/**
 * Created by yumodev on 18/1/29.
 * * [ 神奇的 ViewDragHelper，让你轻松定制拥有拖拽能力的 ViewGroup](http://blog.csdn.net/briblue/article/details/73730386)
 * 移动一张图片， 移动后返回
 */

public class DragTestView4 extends FrameLayout {
    private final String LOG_TAG = Define.INSTANCE.getLOG_TAG();
    private ImageView mDragView;

    private float mLastMotionY;
    private float mLastMotionX;
    private float mInitialMotionX;
    private float mInitialMotionY;

    private float mSourceX;
    private float mSourceY;

    private int mTouchSlop;

    private State mCurrentState;

    // 状态分别空闲、拖拽两种
    enum State {
        IDLE,
        DRAGGING
    }


    public DragTestView4(@NonNull Context context) {
        super(context);
        setupUI();
    }

    private void setupUI() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = viewConfiguration.getScaledTouchSlop();


        mDragView = new ImageView(getContext());
        mDragView.setImageResource(R.drawable.mzd);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        addView(mDragView, lp);
        mDragView.setClickable(false);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                if (isPointOnViews(event)){
                    mCurrentState = State.DRAGGING;
                    mLastMotionX = event.getX();
                    mLastMotionY = event.getY();
                    mInitialMotionX = event.getX();
                    mInitialMotionY = event.getY();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                if (mCurrentState == State.DRAGGING){
//                    int delax = (int)(event.getX() - mInitialMotionX);
//                    int delaY = (int)(event.getY() - mInitialMotionY);

//                    mDragView.setTranslationX(delax);
//                    mDragView.setTranslationY(delaY);

                    int delax = (int)(event.getX() - mLastMotionX);
                    int delaY = (int)(event.getY() - mLastMotionY);

                    ViewCompat.offsetLeftAndRight(mDragView, delax);
                    ViewCompat.offsetTopAndBottom(mDragView, delaY);
                    mLastMotionX = event.getX();
                    mLastMotionY = event.getY();
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                mCurrentState = State.IDLE;
                moveBack();
            }
        }
        return true;
    }

    private void moveBack(){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mDragView, "x", mDragView.getX(), mDragView.getX() - mSourceX);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mDragView, "y", mDragView.getY(), mDragView.getY() - mSourceY);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.setDuration(300);
        animatorSet.start();
    }

    private boolean isPointOnViews(MotionEvent ev){
        Rect rect = new Rect();
        for (int i = 0; i < getChildCount(); i++){
            View view = getChildAt(i);
            view.getHitRect(rect);
            if (rect.contains((int)ev.getX(), (int)ev.getY())) {
                mSourceX = ev.getX();
                mSourceY = ev.getY();
                return true;
            }
        }
        return false;
    }






}
