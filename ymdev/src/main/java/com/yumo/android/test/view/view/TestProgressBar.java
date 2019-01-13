package com.yumo.android.test.view.view;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.yumo.android.R;
import com.yumo.common.android.YmDisplayUtil;
import com.yumo.common.android.YmResUtil;
import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/11/24.
 */

public class TestProgressBar extends YmTestFragment {

    private FrameLayout mProgresBarContainer;
    private ProgressBar mProgrssBar;
    @Override
    protected View getHeaderView() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        SeekBar seekBar = new SeekBar(getContext());
        seekBar.setMax(100);
        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mProgrssBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        linearLayout.addView(seekBar);

        mProgresBarContainer = new FrameLayout(getContext());
        linearLayout.addView(mProgresBarContainer);

        return linearLayout;
    }

    public void testProgressbar(){
        // style="@android:style/Widget.ProgressBar.Horizontal"
        mProgrssBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);

        mProgrssBar.setProgress(0);
        //mProgrssBar.setMax(100);
        //mProgrssBar.setProgressDrawable(getResources().getDrawable(R.color.red));

        mProgresBarContainer.removeAllViews();
        mProgresBarContainer.addView(mProgrssBar);
    }

    public void testProgressbar1(){
        // style="@android:style/Widget.ProgressBar.Horizontal"
        mProgrssBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);

        mProgrssBar.setProgress(0);

        ClipDrawable drawable = new ClipDrawable(new ColorDrawable(getResources().getColor(R.color.blue)), Gravity.LEFT, ClipDrawable.HORIZONTAL);
        mProgrssBar.setProgressDrawable(drawable);//必须先设置到progressbar上再设置level，告诉这个drawable的宽度有多宽，这个level才能生效
        drawable.setLevel(100 * 100);
        mProgrssBar.setProgressDrawable(drawable);
        mProgrssBar.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)YmResUtil.dipToPx(getContext(), 3)));

        mProgresBarContainer.removeAllViews();
        mProgresBarContainer.addView(mProgrssBar);
    }
}
