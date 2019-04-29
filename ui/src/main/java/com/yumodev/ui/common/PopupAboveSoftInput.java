package com.yumodev.ui.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.yumodev.ui.util.YmViewUtils;


/**
 * Created by guo on 17/3/25.
 */

public class PopupAboveSoftInput {
    private final String LOG_TAG = "SoftInputAbovePopupWindow";
    private int mSoftInputHeight = 0;
    private PopupWindow mPopupWindow;

    private Context mContext;

    public PopupAboveSoftInput(Context context){
        mContext = context;
        mPopupWindow = new PopupWindow(context);
    }

    public PopupAboveSoftInput setPopupWindow(PopupWindow popupWindow){
        mPopupWindow = popupWindow;
        return this;
    }

    public PopupWindow getPopupWindow(){
        return mPopupWindow;
    }

    public PopupAboveSoftInput setSoftInputHeight(int height){
        mSoftInputHeight = height;
        return this;
    }

    public PopupAboveSoftInput setHeight(int height){
        mPopupWindow.setHeight(height);
        return this;
    }

    public PopupAboveSoftInput setWidth(int width){
        mPopupWindow.setWidth(width);
        return this;
    }

    public PopupAboveSoftInput setContentView(View view){
        mPopupWindow.setContentView(view);
        return this;
    }


    public void showAtLocation(Activity activity, View parent,int x, int y) {
        Point screen = YmViewUtils.getRealScreenSize(mContext);
        int[] location = new int[2];
        parent.getLocationInWindow(location);

        WindowManager.LayoutParams a = activity.getWindow().getAttributes();
        int screenY = screen.y;
        int popViewHeight = mPopupWindow.getHeight();
        int navigationBarHeight = YmViewUtils.getNavigationBarHeight(activity);
        int marginY = screenY - mSoftInputHeight - popViewHeight - navigationBarHeight;
        mPopupWindow.showAtLocation(parent, Gravity.LEFT | Gravity.TOP, x, marginY);
    }

    public boolean isShowing(){
        if (mPopupWindow != null && mPopupWindow.isShowing()){
            return true;
        }
        return false;
    }

    public void dismiss(){
        if (mPopupWindow != null){
            mPopupWindow.dismiss();
        }
    }
}
