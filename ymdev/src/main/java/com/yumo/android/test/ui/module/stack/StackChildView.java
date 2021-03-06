package com.yumo.android.test.ui.module.stack;

import android.content.Context;
import androidx.annotation.NonNull;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumo.android.R;

/**
 * Created by yumodev on 18/1/10.
 */

public class StackChildView<T extends StackEntry> extends FrameLayout{

    private T mData;

    private ImageView mTitleIconView;
    private ImageView mStaticViewIv;
    private TextView mTitleView;

    public StackChildView(@NonNull Context context, T data) {
        super(context);
        mData = data;
        initView();
    }

    private void initView(){
        View view = View.inflate(getContext(), R.layout.stack_child_view, null);
        addView(view);

        mTitleView = view.findViewById(R.id.title_tv);
        updateView();
    }

    public void updateData(T data){
        mData = data;
        updateView();
    }
    public void updateView(){
        mTitleView.setText(mData.title);
    }
}
