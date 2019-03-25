package com.yumo.android.test.view.flip;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.yumo.android.R;
import com.yumo.common.log.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CustomFlipView extends FrameLayout {
    private final String TAG = "flip";

    private List<String> mDataList = new ArrayList<>();
    private ViewFlipper mViewFlipper;
    private String text = "first";

    public CustomFlipView(Context context) {
        super(context);
        View view = View.inflate(getContext(), R.layout.flipview, null);
        addView(view );

        mViewFlipper = view.findViewById(R.id.view_flipper);

        ((TextView)view.findViewById(R.id.first1)).setSelected(true);
        ((TextView)view.findViewById(R.id.first1)).invalidate();

        ((TextView)view.findViewById(R.id.first)).setSelected(true);


        view.findViewById(R.id.add).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                text = "test；"+ (new Random()).nextInt(1000);
                mDataList.add(text);

                mViewFlipper.showNext();
            }
        });

        view.findViewById(R.id.stop).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               mViewFlipper.stopFlipping();
            }
        });

        mViewFlipper.getInAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i(TAG, "start");
               TextView view1 =  (TextView) mViewFlipper.getCurrentView();
               view1.setText(text);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i(TAG, "end");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i(TAG, "repeat");

            }
        });

        view.findViewById(R.id.view_flipper).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewFlipper.stopFlipping();
            }
        });
    }

    private void initData(){

    }
}
