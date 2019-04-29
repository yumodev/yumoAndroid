package com.yumodev.ui.recyclerview.nestedscroll;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.yumodev.ui.recyclerview.Define;

/**
 * Created by yumodev on 17/12/15.
 * [](http://www.jianshu.com/p/aff5e82f0174)
 */

public class NestedTransLayout extends FrameLayout implements NestedScrollingParent {
    private final String LOG_TAG = "NestedLayout"+ Define.INSTANCE.getLOG_TAG();

    private ViewGroup mStickView;
    private int mStickViewHeight = 0;
    private RecyclerView mChildScrollView;
    private ViewGroup mChildView;

    private OverScroller mScroller;

    private int maxScrollY = 0;

    /**
     * 是否固定不动
     */
    private boolean mCanStick = false;

    /**
     * 是否发生了向下滑动
     */
    private boolean mHasFlingDown = false;

    public NestedTransLayout(Context context) {
        super(context);
        init();
    }

    public NestedTransLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedTransLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mScroller = new OverScroller(getContext());
    }

    public void setStickView(ViewGroup viewGroup, int height){
        mStickView = viewGroup;
        mStickViewHeight = height;
        maxScrollY = height;
        mStickView.setVisibility(View.VISIBLE);
        mChildView.setTranslationY(-mStickViewHeight);
       // scrollBy(0, maxScrollY);
        Log.i(LOG_TAG, "StickViewHeight:"+height);
    }

    public void setCanStick(boolean stick){
        mCanStick = stick;
    }

    public void setChildScrollView(RecyclerView recyclerView){
        mChildScrollView = recyclerView;

        mChildScrollView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    flingStickLayout();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void setChildView(ViewGroup viewGroup){
        mChildView = viewGroup;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //Log.i(LOG_TAG, "setChildScrollView:"+mChildScrollView.getMeasuredHeight()+" "+ getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(LOG_TAG, "onMeasure:"+getMeasuredHeight()+" "+mChildScrollView.getMeasuredHeight());
//        // 动态设置RecyclerView的高度
        ViewGroup.LayoutParams params = mChildScrollView.getLayoutParams();
        // StickyNavLayout高度 - Sticky View高度 - ImageView可见高度
        params.height = mChildScrollView.getMeasuredHeight() + mStickViewHeight ;
        mChildScrollView.setLayoutParams(params);

        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + mStickViewHeight);
        Log.i(LOG_TAG, "onMeasure:"+getMeasuredHeight()+" "+mChildScrollView.getMeasuredHeight());
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        //return super.onStartNestedScroll(child, target, nestedScrollAxes);
        Log.i(LOG_TAG, "onStopNestedScroll: nestedScrollAxes:"+nestedScrollAxes+" "+child.getClass().getSimpleName());
        //return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
        mHasFlingDown = false;
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    /**
     * ViewCompat.canScrollVertically(view, int)
     * 负数: 顶部是否可以往下滚动(官方描述: 能否往上滚动, 不太准确吧~)
     * 正数: 底部是否可以往上滚动
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // dy > 0表示子View向上滑动;
        Log.i(LOG_TAG, "onNestedPreScroll: "+target.getClass().getSimpleName()+" dx:"+dx+" dy:"+dy);
        Log.i(LOG_TAG, "onNestedPreScroll: consumed:x:"+consumed[0]+" consumed:y:"+consumed[1]);
        Log.i(LOG_TAG, "scrollY:"+getScrollY());

        if (mCanStick){
            return;
        }

        boolean canDown = mChildScrollView.canScrollVertically(0);
        boolean canUp = mChildScrollView.canScrollVertically(-1);


        boolean hiddenTop = dy > 0 && getScrollY() < maxScrollY;
        boolean showTop = dy < 0 && getScrollY() > 0 && !canUp;
        Log.i(LOG_TAG, "top:"+hiddenTop);

//        if (!canUp && dy < -mStickViewHeight / 2 ){
//            if (mStickView.getVisibility() != View.VISIBLE){
//                mStickView.setVisibility(View.VISIBLE);
//                //showStickView();
//            }
//        }

        if (hiddenTop || showTop){
            scrollBy(0, dy);
            consumed[1] = dy;
        }

//        if (dy > 0){
//            if (getScrollY() < maxScrollY){
//                scrollBy(0, dy);
//                consumed[1] = dy;
//            }
//        }else if (!canUp){
//            if (getScrollY() > 0){
//                scrollBy(0, dy);
//               consumed[1] = dy;
//            }
//        }


        Log.i(LOG_TAG, "onNestedPreScroll:"+canDown+" "+canUp);
    }

    @Override
    public void onStopNestedScroll(View child) {
        super.onStopNestedScroll(child);
        Log.i(LOG_TAG, "onStopNestedScroll:"+child.getClass().getSimpleName());
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.i(LOG_TAG, "onNestedPreFling:"+velocityY);
        if (!mCanStick){
            if (velocityY > 0 && getScrollY() < maxScrollY){ // 向上滑动, 且当前View还没滑到最顶部{
                Log.i(LOG_TAG, "onNestedPreFling: up"+getScrollY()+" velocityY:"+velocityY);
                fling((int) velocityY, maxScrollY);
                return true;
            } else if (velocityY < 0 && getScrollY() > 0){ // 向下滑动, 且当前View部分在屏幕外
                //fling((int) velocityY, maxScrollY);
                mHasFlingDown = true;
                //fling((int)velocityY, 0);
                Log.i(LOG_TAG, "onNestedPreFling: download"+getScrollY()+" velocityY:"+velocityY);
                //return true;
            }
        }

        return false;
    }


    private void showStickView(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mStickView, "translationY", -mStickViewHeight, 0);
        animator.setDuration(500);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(LOG_TAG, "onAnimationStart");
                mStickView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    @Override
    protected int computeVerticalScrollRange() {
        //return super.computeVerticalScrollRange();
        return getMeasuredHeight() - mStickViewHeight;
    }

    @Override
    public void computeScroll() {
        Log.i(LOG_TAG, "computeScroll");
        if (mScroller.computeScrollOffset()){
            Log.i(LOG_TAG, "computeScrollScrollOffset:"+mScroller.getCurrY());
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    public void flingStickLayout(){
        if (!mCanStick){
            if (mHasFlingDown){
                boolean canDown = mChildScrollView.canScrollVertically(0);
                int positon = ((LinearLayoutManager)mChildScrollView.getLayoutManager()).findFirstVisibleItemPosition();
                if (positon < 3 || !canDown || (getScrollY() > 0 && getScrollY() < maxScrollY)){
                    mScroller.startScroll(getScrollX(), getScrollY(), 0, 0 - getScrollY());
                    invalidate();
                }
            }
        }

    }

    public void fling(int velocityY, int maxY){
        Log.i(LOG_TAG, "fling velocityY:"+velocityY);
        mScroller.fling(0, getScrollY(), 0, velocityY, 0,0,0, maxY);
        invalidate();
    }

//    @Override
//    public void scrollBy(int x, int y) {
//        if (getScrollY() + y < 0){
//            scrollTo(0, 0);
//        }else{
//            super.scrollBy(x, y);
//        }
//    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0){ // 不允许向下滑动{
            y = 0;
        }

        if (y > maxScrollY){ // 防止向上滑动距离大于最大滑动距离
            y = maxScrollY;
        }

        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }
}
