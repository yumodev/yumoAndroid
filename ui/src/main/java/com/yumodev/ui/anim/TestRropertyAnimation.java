package com.yumodev.ui.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.yumo.common.android.YmDisplayUtil;
import com.yumo.demo.view.YmTestFragment;
import com.yumodev.ui.R;
import com.yumodev.ui.anim.view.PointAnimView;
import com.yumodev.ui.recyclerview.Define;

/**
 * Created by yumodev on 17/12/17.
 *
 * ObjectAnimator是ValueAnimator的一个子类
 * 可以设置目标对象和及其属性进行动画。
 *
 * ofFloat(Object target, String propertyName, float... values)
 * 第一个参数：设置动画作用的目标对象
 * 第二个参数：设置动画作用的属性。
 * 第三个是对应的值。
 *
 * setDuration：设置动画运行时间
 * setRepeatCount:设置重复次数
 * setRepeatMode:设置重复的模式 RESTART和REVERSE
 * setStartDelay：设置延迟执行
 * setInterpolator：设置动画插值器
 *
 *
 *
 * 组合动画
 *
 * AnimationSet
 *
 * after(Animator anim):将现有动画插入到传入的动画之后执行
 * after(long delay):将现有动画延迟制定毫秒后执行
 * before(Animator anim): 将现有动画插入到传入的动画之前执行
 * with(Animator anim): 将现有动画和传入的动画同时执行
 *
 *
 * 插值器
 *
 * BounceInterpolator：反弹插值器
 * AccelerateInterpolator：加速度
 * AccelerateDecelerateInterpolator：先加速后减速的插值器，默认插值器
 * LinearInterpolator:匀速插值器
 *
 *
 *
 *
 */

public class TestRropertyAnimation extends YmTestFragment {

    private final String LOG_TAG = Define.INSTANCE.getLOG_TAG();
    private ImageView mImageView = null;
    @Override
    protected View getHeaderView() {
        return createHeadView();
    }

    private View createHeadView(){
        mImageView = new ImageView(getContext());
        mImageView.setImageResource(R.mipmap.ic_launcher);
        return mImageView;
    }


    public void testAnim1(){
        mImageView.animate()
                .translationX(500)
                .setListener(createAnimatorListener())
                .setDuration(500);
    }

    public void testAnim2(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "translationX", 500);
        animator.setDuration(2000);
        animator.setRepeatCount(10);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setStartDelay(1000);
        animator.addListener(createAnimatorListener());
        animator.start();
    }

    public void testTranslationX(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "translationX", 500);
        animator.setTarget(mImageView);
        animator.setFloatValues(0, 500, 0);
        animator.setPropertyName("translationX");
        animator.setDuration(2000);
        animator.setRepeatCount(10);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setStartDelay(1000);
        animator.addListener(createAnimatorListener());
        animator.start();
    }

    public void testRotation(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "Rotation", 0f, 360f);
        animator.setDuration(5000);
        animator.setRepeatCount(10);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addListener(createAnimatorListener());
        animator.start();
    }


    private Animator.AnimatorListener createAnimatorListener(){
         return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(LOG_TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(LOG_TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i(LOG_TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i(LOG_TAG, "onAnimationRepeat");
            }
        };
    }


    /**
     * 值动画
     */
    public void testValueAnimator(){
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.addUpdateListener(animation -> Log.i(LOG_TAG, "Update:"+animation.getAnimatedValue()+" "+animation.getCurrentPlayTime()));
        anim.setDuration(2000);
        anim.start();
    }


    /**
     * 组合动画
     */
    public void testAnimationSet(){
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(mImageView, "translationX", -500f, 0f));
        animatorSet.play(ObjectAnimator.ofFloat(mImageView, "alpha", 1f, 0f, 1f));
        animatorSet.setDuration(5000);
        animatorSet.start();
    }

    /**
     * 一个自定义ValueAnimator的动画
     */
    public void testShowPointView(){
        showTestView(new PointAnimView(getContext()));
    }

    /**
     * 加速度插值器
     */
    public void testAccelerateInterpolator(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "translationY", 1000);
        animator.setDuration(5000);
        animator.setRepeatCount(3);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addListener(createAnimatorListener());
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    public void testBounceInterpolator(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "translationY", 1000);
        animator.setDuration(5000);
        animator.setRepeatCount(3);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addListener(createAnimatorListener());
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

}
