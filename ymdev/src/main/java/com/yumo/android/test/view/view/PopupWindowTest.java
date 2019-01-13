package com.yumo.android.test.view.view;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 17/3/15.
 */

public class PopupWindowTest extends YmTestFragment {

    public void testShowPopupWindow(){
        TextView textView = new TextView(getContext());
        textView.setText("test");
        PopupWindow popupWindow = new PopupWindow(getContext());
        popupWindow.setContentView(textView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setClippingEnabled(false);
        popupWindow.showAtLocation(getView(), Gravity.CENTER, 0 , 0);
    }
}
