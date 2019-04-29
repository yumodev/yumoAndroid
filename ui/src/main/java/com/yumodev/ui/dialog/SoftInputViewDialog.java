package com.yumodev.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yumodev.ui.R;
import com.yumodev.ui.common.PopupAboveSoftInput;
import com.yumodev.ui.common.SoftInputView;
import com.yumodev.ui.util.YmViewUtils;

/**
 * Created by guo on 17/3/26.
 */

public class SoftInputViewDialog extends Dialog {
    private final String LOG_TAG = "SoftInputViewDialog";
    private SoftInputView mRootView = null;
    private PopupAboveSoftInput mSoftInputBar;
    public SoftInputViewDialog(@NonNull Context context) {
        super(context);
        setupUi();
    }

    public SoftInputViewDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setupUi();
    }

    private void setupUi(){
        mRootView = new SoftInputView(getContext());
        mRootView.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        mRootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(mRootView);

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

        createEditText();
        mSoftInputBar = new PopupAboveSoftInput(getContext());


        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

    }

    private void createEditText(){
        EditText editView = new EditText(getContext());
        editView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, YmViewUtils.dip2px(getContext(), 20)));
        editView.setText("editText");
        mRootView.addView(editView);
    }

    private View getSoftInputView(){
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, YmViewUtils.dip2px(getContext(), 20)));
        textView.setText("显示在软键盘上面");
        return textView;
    }

    private void showSoftInputView(int inputHeight){
        if (!mSoftInputBar.isShowing()){
            mSoftInputBar.setContentView(getSoftInputView())
                    .setWidth(YmViewUtils.getScreenWidth(getContext()))
                    .setHeight(YmViewUtils.dip2px(getContext(), 20))
                    .setSoftInputHeight(inputHeight)
                    .showAtLocation(getOwnerActivity(), mRootView, 0, 0);
        }

    }

    private void hideSoftInputView(){
        mSoftInputBar.dismiss();
    }
}
