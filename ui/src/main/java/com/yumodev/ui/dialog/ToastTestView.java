package com.yumodev.ui.dialog;

import android.widget.Toast;

import com.yumo.demo.view.YmTestFragment;

/**
 * Created by yumodev on 15/11/21.
 */
public class ToastTestView extends YmTestFragment {

    public void testToast(){
        showToastMessage("test message");
    }

    public void testToast1() {
        Toast.makeText(getContext(), "ToastView", Toast.LENGTH_SHORT).show();
    }
}
