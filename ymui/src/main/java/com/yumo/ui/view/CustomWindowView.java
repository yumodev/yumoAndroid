package com.yumo.ui.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

/**
 * Created by wks on 17/3/24.
 * 封装一个基本的自定义Window显示的View。
 */

public class CustomWindowView {
    private final String LOG_TAG = "CustomWindowView";
    private WeakReference<Context> mContextWeakReference;
    private WindowManager mLocalWindowManager;
    private WindowManager.LayoutParams mParams;
    private View mContentView;
    private int mWidth = WindowManager.LayoutParams.MATCH_PARENT;
    private int mHeight = WindowManager.LayoutParams.WRAP_CONTENT;
    private int mX = 0;
    private int mY = 0;
    private int mGravity = Gravity.LEFT | Gravity.BOTTOM;

    private boolean mShowing = false;

    public CustomWindowView(Context context){
        mContextWeakReference = new WeakReference<Context>(context);
        init();
        initLayoutParams();
    }

    private void init(){
        mLocalWindowManager = (WindowManager) mContextWeakReference.get()
                .getSystemService(Context.WINDOW_SERVICE);
    }

    private void initLayoutParams(){
        mParams = new WindowManager.LayoutParams();
        //mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        //mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mParams.gravity = mGravity;
        mParams.x = mX;
        mParams.y = mY;
        mParams.width = mWidth;
        mParams.height = mHeight;
        mParams.verticalMargin = 0;
        mParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
    }

    public WindowManager getWindowManager(){
        return mLocalWindowManager;
    }

    public void setLayoutParams(WindowManager.LayoutParams lp){
        mParams = lp;
    }

    public WindowManager.LayoutParams getLayoutParams(){
        return mParams;
    }

    private void attachToWindow() {
        mLocalWindowManager.addView(mContentView, mParams);
        mShowing = true;
    }

    private void detachedFromWindow(){
        mLocalWindowManager.removeView(mContentView);
        mContentView = null;
        // mParams = null;
        mShowing = false;
        //mLocalWindowManager = null;
    }


    public void setContentView(View view){
        mContentView = view;
    }

    public View getContentView(){
        return mContentView;
    }

    public void setWidth(int width){
        mWidth = width;
    }

    public int getWidth(){
        return mWidth;
    }

    public void setHeight(int height){
        mHeight = height;
    }

    public int getHeight(){
        return mHeight;
    }

    public boolean isShowing(){
        return mShowing;
    }

    public void dismiss(){
        detachedFromWindow();
    }

    public void show(){
        attachToWindow();
    }

    public void showAtLocation(int x, int y){
        showAtLocation(mGravity, x, y);
    }

    public void showAtLocation(int gravity, int x, int y) {
        mGravity = gravity;
        mY = x;
        mY = y;
        attachToWindow();
    }

    public Context getContext(){
        return mContextWeakReference.get();
    }
}
