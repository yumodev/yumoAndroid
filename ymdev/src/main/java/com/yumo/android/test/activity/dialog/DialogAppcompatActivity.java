package com.yumo.android.test.activity.dialog;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yumo.android.R;
import com.yumo.android.common.YumoConfig;
import com.yumo.ui.util.ViewUtils;
import com.yumo.ui.view.PopupAboveSoftInput;
import com.yumo.ui.view.SoftInputView;

/**
 * Created by yumodev on 17/3/8.
 */

public class DialogAppcompatActivity extends AppCompatActivity {
    private final String LOG_TAG = "DialogAppcompatActivity";

    private SoftInputView mRootView;
    private PopupAboveSoftInput mSoftInputBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = new SoftInputView(this);
        mRootView.setBackgroundColor(getResources().getColor(R.color.white));
        setContentView(mRootView);
        setupUI();

        mRootView.addSoftInputListener(new SoftInputView.SoftInputListener() {
            @Override
            public void onSoftInputChange(boolean show, int keyboardHeight) {
                Log.d(LOG_TAG, "onSoftInputChange: "+ show +" height:"+keyboardHeight);
                if (show){
                    showSoftInputView(keyboardHeight);
                }else{
                    hideSoftInputView();
                }
            }
        });

        mSoftInputBar = new PopupAboveSoftInput(this.getApplicationContext());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        if (YumoConfig.isTablet()){
            //setupTabletWindowSize(getWindow(), ViewUtils.dip2px(this, 400), ViewUtils.dip2px(this, 500), Gravity.CENTER);
            setupTabletWindowSize(getWindow(), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        }

        mRootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                showSoftInputView(300);
            }
        }, 2000);
    }

    private void setupUI(){
        EditText editView = new EditText(this);
        editView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtils.dip2px(this, 20)));
        editView.setText("editText");
        mRootView.addView(editView);
    }

    private View getSoftInputView(){
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtils.dip2px(this, 20)));
        textView.setText("显示在软键盘上面");
        return textView;
    }

    private void showSoftInputView(int inputHeight){
        if (!mSoftInputBar.isShowing()){
            mSoftInputBar.setContentView(getSoftInputView())
                    .setWidth(ViewUtils.getScreenWidth(this))
                    .setHeight(ViewUtils.dip2px(this, 20))
                    .setSoftInputHeight(inputHeight)
                    .showAtLocation(this, mRootView, 0, 0);
        }

    }

    private void hideSoftInputView(){
        mSoftInputBar.dismiss();
    }


    private void setupTabletWindowSize(Window window, int width, int height, int gravity) {
        WindowManager.LayoutParams a = window.getAttributes();
        a.width = width;
        a.height = height;
        a.gravity = gravity;
        window.setAttributes(a);
    }

}
