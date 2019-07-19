package com.yumo.lottie;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.airbnb.lottie.LottieAnimationView;
import com.yumo.demo.view.YmTestFragment;

public class TestLottie extends YmTestFragment {

   public  void test1(){

   }
  public void testLottiehelloJson(){
    LottieAnimationView lottieView = new LottieAnimationView(getContext());
    lottieView.setAnimation("hello-world.json");
    lottieView.loop(true);
    lottieView.playAnimation();

    showTestView(lottieView);
  }

  public void testLottieAnimateLockJson(){
    LottieAnimationView lottieView = new LottieAnimationView(getContext());
    lottieView.setBackgroundColor(Color.BLUE);
    lottieView.setAnimation("animate_lock.json");
    lottieView.setBackgroundColor(Color.BLUE);

    PorterDuffColorFilter
        colorFilter = new PorterDuffColorFilter(ContextCompat.getColor(getContext(),android.R.color.holo_blue_light), PorterDuff.Mode.ADD);
    //lottieView.addColorFilter(colorFilter);
    lottieView.loop(true);
    lottieView.playAnimation();

    showTestView(lottieView);

  }

  public void testLottieAnimateLockJson1(){
     View view = View.inflate(getContext(), R.layout.animate_lock,null);
     showTestView(view);
  }
}
