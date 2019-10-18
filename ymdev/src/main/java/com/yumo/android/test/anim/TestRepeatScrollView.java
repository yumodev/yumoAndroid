package com.yumo.android.test.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import com.yumo.android.R;

import com.yumo.android.test.anim.view.UnlimitedScrollllview;
import com.yumo.common.log.Log;
import com.yumo.demo.view.YmTestFragment;
import java.util.ArrayList;
import java.util.List;

public class TestRepeatScrollView extends YmTestFragment {
  private final String TAG = "RepeatScrollView";
  UnlimitedScrollllview mRepeatView = null;
  List<ObjectAnimator> objectAnimators = new ArrayList<>();
  @Override
  protected View getHeaderView() {
    HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getContext());
    mRepeatView = new UnlimitedScrollllview(getContext());
    mRepeatView.initView();
    mRepeatView.setClipChildren(true);
    horizontalScrollView.addView(mRepeatView);
    return horizontalScrollView;
  }


  public void testAddView(){
    mRepeatView.addView(createView());
  }

  public void testRemoteFirst(){
    mRepeatView.removeViewAt(0);
  }
  private View createView(){
    ImageView imageView = new ImageView(getContext());
    imageView.setImageResource(R.drawable.magic_vpn_card_3);
    return imageView;
  }

  public void testPrintPosition(){
    for (int i = 0; i < mRepeatView.getChildCount(); i++){
      View view = mRepeatView.getChildAt(i);
      Rect rect = new Rect();

      //view.getGlobalVisibleRect(rect);
      rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
      Log.i(TAG, rect.toString()+" translationX:"+view.getTranslationX());
    }
  }

  @Override public void onStop() {
    super.onStop();
    try {
      for (ObjectAnimator animator : objectAnimators){
        animator.cancel();
      }
    }catch (Exception e){
      e.printStackTrace();
    }

  }

  public void testStartAnim(){
    for (int i = 0; i < mRepeatView.getChildCount(); i++){
      final View view = mRepeatView.getChildAt(i);
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

          mRepeatView.addView(view1);


          view1.post(new Runnable() {
            @Override public void run() {
              View lastView = mRepeatView.getChildAt(mRepeatView.getChildCount()-2);
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
    return (long)(2000 * (distance / 1000f));
  }

  public void testStartPlay(){
    mRepeatView.startPlay();
  }



}
