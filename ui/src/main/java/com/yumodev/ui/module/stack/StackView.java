package com.yumodev.ui.module.stack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import com.yumodev.ui.recyclerview.Define;
import com.yumodev.ui.util.YmViewUtils;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.widget.ViewDragHelper.INVALID_POINTER;

/**
 * Created by yumodev on 18/1/10.
 * 堆叠视图
 */

public class StackView extends FrameLayout {
    private final String LOG_TAG = "StackView";

    private List<StackEntry> mDataList = new ArrayList<>();
    public StackHelper mStackHelper;

    private OverScroller mScroller;

    private float mInitialY;
    private float mInitialX;
    private float mLastY;
    private float mLastX;
    private int mActivePointerId;
    private int mYVelocity = 0;
    private int mSlideAmount;
    private int mSwipeThreshold;
    private int mTouchSlop;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private VelocityTracker mVelocityTracker;



    private int mViewHeight;

    private boolean mHasInitConfig = false;
    private boolean mScrolling = false;

    public StackView(@NonNull Context context) {
        super(context);
        init();
    }

    public StackView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StackView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mStackHelper = new StackHelper();
        mScroller = new OverScroller(getContext());

        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i =0; i < getChildCount(); i++){
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }

        if (!mHasInitConfig){
            mStackHelper.initConfig(getMeasuredHeight());
            mHasInitConfig = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed){
            layoutChildView();
        }
    }

    public void setDataList(List<StackEntry> dataList){
        mDataList = dataList;
        int childCount = mDataList.size();

        for (int i = 0; i < childCount; i++) {
            StackChildView childView = (StackChildView) getChildAt(i);
            StackEntry data = mDataList.get(i);
            if (childView == null) {
                childView = new StackChildView(getContext(), data);
                addView(childView);
            } else {
                childView.updateData(data);
            }
        }
        invalidate();
    }

//    private void layoutChildView(int left, int top, int right, int bottom){
//        int childCount = mDataList.size();
//
//        for (int i = 0; i < childCount; i++){
//            StackChildView childView = (StackChildView) getChildAt(i);
//            bottom = top + YmViewUtils.dip2px(getContext(), 300);
//            childView.layout(left, top, right, bottom);
//            int transLayoutY = i * 100;
//            childView.setTranslationY(transLayoutY);
//            float scaleX = 0.8f+(0.05f * i);
//            childView.setScaleX(scaleX);
//        }
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int pointerIndex = ev.findPointerIndex(mActivePointerId);
        if (pointerIndex == INVALID_POINTER) {
            // no data for our primary pointer, this shouldn't happen, log it
            Log.d(LOG_TAG, "Error: No data for our primary pointer.");
            return false;
        }
        float x = ev.getX(pointerIndex);
        float y = ev.getY(pointerIndex);
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                mInitialX = x;
                mInitialY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                return true;
            }
            case MotionEvent.ACTION_UP:{
                break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int pointerIndex = ev.findPointerIndex(mActivePointerId);
        if (pointerIndex == INVALID_POINTER) {
            // no data for our primary pointer, this shouldn't happen, log it
            Log.d(LOG_TAG, "Error: No data for our primary pointer.");
            return false;
        }

        float x = ev.getX(pointerIndex);
        float y = ev.getY(pointerIndex);
        Log.i(LOG_TAG, ev.getActionMasked()+" x:"+x+" y:"+y+" mScrollX:"+mScroller.getStartX()+" "+mScroller.getStartY());
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                mInitialX = x;
                mInitialY = y;
                mLastY = y;
                mLastX = x;
                mScrolling = false;
                initOrResetVelocityTracker();
                mVelocityTracker.addMovement(ev);
                return true;
            }
            case MotionEvent.ACTION_MOVE:{
                mVelocityTracker.addMovement(ev);
                if (Math.abs(y - mInitialY) > mTouchSlop){
                    mScrolling = true;
                }
                float delaY = mLastY - y;
                // mTotalMotionY 就是我们滑动的总距离
//                if (mStackHelper.isOverPositiveScrollP()) {
//                    // calculateDamping() 为计算阻尼的方法，即当overscroll时，实现越来越难滑的效果
//                    mStackHelper.mDeltaInstance -= (delaY * mStackHelper.calculateDamping());
//                } else {
//                    mStackHelper.mDeltaInstance -= delaY;
//                }
                mStackHelper.mDeltaInstance -= delaY;
//
                Log.i(Define.INSTANCE.getLOG_TAG(), "mDeltaInstance"+ mStackHelper.mDeltaInstance);
                //mScroller.startScroll(mScroller.getStartX(), mScroller.getStartY(), 0, (int)delaY);
                layoutChildView();

                mLastX = x;
                mLastY = y;
                break;
            }
            case MotionEvent.ACTION_UP:{
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                if (mScrolling){
                    if (mVelocityTracker.getYVelocity(mActivePointerId) > mMinimumVelocity){
                        fling((int)mVelocityTracker.getYVelocity(mActivePointerId));
                    }else{

                    }
                }
                recycleVelocityTracker();
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:{
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:{
                break;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 如果我们手指离开屏幕时滑动速度很快，让view飞一会，X方向忽略
     * @param velocity
     */
    public void fling(int velocity) {
        mScroller.fling(
                0,
                (int) mStackHelper.mDeltaInstance,
                0,
                velocity,
                0,
                0,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            Log.i(LOG_TAG, "computeScroll:");
            mStackHelper.mDeltaInstance = mScroller.getCurrY();
            layoutChildView();
        }
        super.computeScroll();
    }

    private void layoutChildView(){
        int childCount = getChildCount();

        float progress = mStackHelper.getProgress(mStackHelper.mDeltaInstance);
        mStackHelper.setProgress(progress);
        for (int i = 0; i < childCount; i++){
            StackChildView childView = (StackChildView) getChildAt(i);
            float transLayoutY = mStackHelper.getTransLayY(i, progress);
            childView.setTranslationY(transLayoutY);
            float scaleX = mStackHelper.getScale(i, progress);
            childView.setScaleX(scaleX);
        }
        invalidate();
    }

    // 速度追踪器
    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

}
