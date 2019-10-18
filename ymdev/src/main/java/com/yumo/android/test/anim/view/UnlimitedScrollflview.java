package com.yumo.android.test.anim.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yumo.android.R;

/**
 * 无间断的滚动View
 */
public class UnlimitedScrollflview extends FrameLayout {
  private View mView;
  public UnlimitedScrollflview(@NonNull Context context) {
    super(context);
  }

  public UnlimitedScrollflview(@NonNull Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public UnlimitedScrollflview(@NonNull Context context, @Nullable AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void initView(){
    mView = createView();
  }

  public void startPlay(){
    TranslateAnimation
        translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_PARENT,0,Animation.RELATIVE_TO_PARENT,0.5f);
    translateAnimation.setDuration(3000);
    translateAnimation.setFillAfter(true);
  }

  private View createView(){
    ImageView imageView = new ImageView(getContext());
    imageView.setImageResource(R.drawable.magic_vpn_card_3);
    return imageView;
  }
}
