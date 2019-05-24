package com.yumo.android.test.view.layout1;

import androidx.lifecycle.LifecycleOwner;

import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.yumo.android.R;
import com.yumo.ui.arch.YmLifeCycle;

public class LayoutMenuNavView implements YmLifeCycle {
    private ViewGroup mRootView;
    private RadioGroup mRadioGroup;
    private RadioGroup mVerRadioGroup;
    private HorizontalScrollView mHorScrollView;
    private ScrollView mVerScrollView;
    private int mLastOrientation;
    private CheckListener mCheckListener = null;


    public void setContentView(View view){
        mRootView = (ViewGroup) view;
        mRadioGroup = (RadioGroup) view.findViewById(R.id.hor_radiogroup_id);
        mVerRadioGroup = (RadioGroup) view.findViewById(R.id.ver_radiogroup_id);
        mHorScrollView = view.findViewById(R.id.hor_scroll);
        mVerScrollView = view.findViewById(R.id.ver_scroll);
        mLastOrientation = view.getResources().getConfiguration().orientation;
        changeOrigin(mLastOrientation);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton bt = (RadioButton) group.findViewById(checkedId);
                mHorScrollView.smoothScrollTo(bt.getLeft(), 0);

                if (mCheckListener != null){
                    mCheckListener.onPageChecked(group.indexOfChild(bt));
                }
            }
        });

        mVerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton bt = (RadioButton) group.findViewById(checkedId);
                if (mCheckListener != null){
                    mCheckListener.onPageChecked(group.indexOfChild(bt));
                }
            }
        });
    }

    public View getContentView(){
        return mRootView;
    }

    private void changeOrigin(int orientation){
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            mHorScrollView.setVisibility(View.GONE);
            mVerScrollView.setVisibility(View.VISIBLE);
//            mHorScrollView.removeView(mRadioGroup);
//            mVerScrollView.addView(mRadioGroup);
//            mRadioGroup.setOrientation(RadioGroup.VERTICAL);
            ((RadioButton)mVerRadioGroup.findViewById(R.id.radio_5)).setChecked(true);
        }else if (orientation == Configuration.ORIENTATION_PORTRAIT){
            mVerScrollView.setVisibility(View.GONE);
            mHorScrollView.setVisibility(View.VISIBLE);
//            mVerScrollView.removeView(mRadioGroup);
//            mHorScrollView.removeAllViews();
//            mHorScrollView.addView(mRadioGroup);
            mRadioGroup.setOrientation(RadioGroup.HORIZONTAL);
            //((RadioButton)mRadioGroup.findViewById(R.id.radio_5)).setChecked(true);
            mRadioGroup.check(R.id.radio_5);
        }
    }


    public void onConfigurationChanged(Configuration newConfig) {
        if (mLastOrientation != newConfig.orientation){
            changeOrigin(newConfig.orientation);
            mLastOrientation = newConfig.orientation;
        }
    }

    @Override
    public void create(LifecycleOwner lifecycleOwner) {

    }

    @Override
    public void start(LifecycleOwner lifecycleOwner) {

    }

    @Override
    public void resume(LifecycleOwner lifecycleOwner) {

    }

    @Override
    public void pause(LifecycleOwner lifecycleOwner) {

    }

    @Override
    public void stop(LifecycleOwner lifecycleOwner) {

    }

    @Override
    public void destroy(LifecycleOwner lifecycleOwner) {

    }

    public void addCheckListener(CheckListener listener){
        mCheckListener = listener;
    }

    static interface CheckListener{
        public void onPageChecked(int index);
    }

}
