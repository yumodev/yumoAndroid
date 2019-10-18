package com.yumo.android.test.anim.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;

import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yumo.android.R;
import com.yumo.common.log.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * 无间断的滚动View
 */
public class UnlimitedScrollllview extends LinearLayout {
  final String TAG = "RepeatScrollView";
  List<ObjectAnimator> objectAnimators = new ArrayList<>();
  public UnlimitedScrollllview(@NonNull Context context) {
    super(context);
  }

  public UnlimitedScrollllview(@NonNull Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public UnlimitedScrollllview(@NonNull Context context, @Nullable AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void initView(){
  }


  private View createView(){
    ImageView imageView = new ImageView(getContext());
    imageView.setImageResource(R.drawable.magic_vpn_card_3);
    return imageView;
  }

  public void startPlay(){
    for (int i = 0; i < getChildCount(); i++){
      final View view = getChildAt(i);
      startAnimView(view);
    }
  }

  private void startAnimView(View view){
    try {
      Rect rect = new Rect();

      //view.getGlobalVisibleRect(rect);
      rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
      Log.i(TAG, rect.toString());
      float distance = (rect.right+view.getTranslationX());
      ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX",-rect.right);
      long duration = getDuration(distance);
      Log.i(TAG, rect.toString()+" "+duration+"");
      animator.setDuration(duration);
      animator.setInterpolator(new LinearInterpolator());
      animator.addListener(new Animator.AnimatorListener(){
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
          testPrintPosition();
          View view1 = createView();

         addView(view1);

          view1.post(new Runnable() {
            @Override public void run() {
              View lastView = getChildAt(getChildCount()-2);
              float lastTranslationX = lastView.getTranslationX();
              view1.setTranslationX(lastTranslationX);
              startAnimView(view1);
            }
          });

          Log.i(TAG, "begin onAnimationEnd:");
          testPrintPosition();
          Log.i(TAG, "end onAnimationEnd:");

          objectAnimators.remove(animator);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
          objectAnimators.remove(animator);
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
      });
      animator.start();
      objectAnimators.add(animator);
    }catch (Exception e){
      e.printStackTrace();
    }

  }

  private long getDuration(float distance){
    return (long)(3000 * (distance / 1000f));
  }

  public void testPrintPosition(){
    for (int i = 0; i < getChildCount(); i++){
      View view = getChildAt(i);
      Rect rect = new Rect();

      //view.getGlobalVisibleRect(rect);
      rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
      Log.i(TAG, rect.toString()+" translationX:"+view.getTranslationX());
    }
  }

}
