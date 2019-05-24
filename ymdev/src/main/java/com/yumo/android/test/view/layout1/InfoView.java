package com.yumo.android.test.view.layout1;

import androidx.lifecycle.LifecycleOwner;

import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;

import com.yumo.ui.arch.YmLifeCycle;

public class InfoView implements YmLifeCycle {
    private ViewGroup mRootView;
    private int mLastOrientation;

    public void setContentView(View view){
        mRootView = (ViewGroup)view;
        mLastOrientation = mRootView.getResources().getConfiguration().orientation;
        changeOrientation(mLastOrientation);
    }

    public View getContentView(){
        return mRootView;
    }

    private void changeOrientation(int orientation){
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
//            mRootView.removeAllViews();
//            mRootView.addView(View.inflate(mRootView.getContext(), R.layout.layout1_info_view, null));
        }else if (orientation == Configuration.ORIENTATION_PORTRAIT){
//            mRootView.removeAllViews();
//            mRootView.addView(View.inflate(mRootView.getContext(), R.layout.layout1_info_view, null));
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (mLastOrientation != newConfig.orientation){
            changeOrientation(newConfig.orientation);
            mLastOrientation = newConfig.orientation;
        }
    }
}
