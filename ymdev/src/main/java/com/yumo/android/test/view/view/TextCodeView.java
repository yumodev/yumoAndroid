package com.yumo.android.test.view.view;

import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.ImageView;

import com.yumo.android.R;
import com.yumo.common.ui.CodeUtils;
import com.yumo.demo.view.YmTestFragment;

public class TextCodeView extends YmTestFragment {

    public void testShowCodeView(){
        ImageView imageView = new ImageView(getContext());
        CodeUtils codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        imageView.setImageBitmap(bitmap);
        showTestView(imageView);

        showToastMessage(codeUtils.getCode());
    }
}

