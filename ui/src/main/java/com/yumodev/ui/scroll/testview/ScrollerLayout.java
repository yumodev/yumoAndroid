package com.yumodev.ui.scroll.testview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.yumodev.ui.recyclerview.Define;

/**
 * Created by yumodev on 17/12/19.
 */

public class ScrollerLayout extends ViewGroup {
    private final String LOG_TAG = "TestScrollerLayout:"+ Define.INSTANCE.getLOG_TAG();

    private Scroller mScroller;
    private int mTouchSlop;
    /**
     * 按下时的屏幕坐标
     */
    private float mXDown;
    /**
     * move的坐标
     */
    private float mXMove;

    /**
     * 移动的坐标
     */
    private float mXLastMove;

    /**
     * 界面可滑动的左右边界
     */
    private int mLeftBorder;
    private int mRightBorder;

    public ScrollerLayout(Context context) {
        super(context);
        init();
    }

    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mScroller = new Scroller(getContext());
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledPagingTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i =0; i <getChildCount(); i++){
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){
            for (int i = 0; i < getChildCount(); i++){
                View childView = getChildAt(i);
                childView.layout( i * childView.getMeasuredWidth(), 0, (i+1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }

            mLeftBorder = getChildAt(0).getLeft();
            mRightBorder = getChildAt(getChildCount() - 1).getRight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:{
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;
            }

            case MotionEvent.ACTION_MOVE:{
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                if (diff > mTouchSlop){
                    Log.i(LOG_TAG, "onInterceptTouchEvent:"+mTouchSlop);
                    //拦截事件
                    return true;
                }
                break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_MOVE:{
                mXMove = event.getRawX();
                int scrollX = (int)(mXLastMove - mXMove);
                if (getScrollX() + scrollX < mLeftBorder){
                    scrollTo(mLeftBorder, 0);
                }else if (getScrollX() + getWidth()+scrollX > mRightBorder){
                    scrollTo(mRightBorder - getWidth(), 0);
                }
                scrollBy(scrollX, 0);
                mXLastMove = mXMove;
                break;

            }
            case MotionEvent.ACTION_UP:{
                Log.i(LOG_TAG, "ACTION_UP");
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
            }
            case MotionEvent.ACTION_CANCEL:{
                Log.i(LOG_TAG, "ACTION_CANCEL");
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.i(LOG_TAG, "computeScroll");
        if (mScroller.computeScrollOffset()){
            Log.i(LOG_TAG, "computeScrollOffset");
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
