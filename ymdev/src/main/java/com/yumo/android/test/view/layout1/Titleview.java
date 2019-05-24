package com.yumo.android.test.view.layout1;

import androidx.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yumo.android.R;
import com.yumo.ui.arch.YmLifeCycle;

public class Titleview implements YmLifeCycle {

    private ViewGroup mRootView;
    private Context mContent;

    public void createView(Context context){
        mRootView = (FrameLayout) View.inflate(context, R.layout.layout_title_view, null);
    }

    public void setContentView(ViewGroup view){
        mRootView = view;
    }

    public View getContentView(){
        return mRootView;
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

    }
}
