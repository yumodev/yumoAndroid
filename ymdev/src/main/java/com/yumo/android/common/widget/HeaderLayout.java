package com.yumo.android.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yumo.android.R;

/**
 * Created by guo on 15/9/20.
 * 头部自定义控件
 */
public class HeaderLayout extends LinearLayout {
    private static final String TAG = "HeaderLayout";

    private ImageView mBackView;
    private TextView mTitleView;

    public HeaderLayout(Context context) {
        super(context);
    }


    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackView = (ImageView)findViewById(R.id.back_img);
        mTitleView = (TextView)findViewById(R.id.title);

    }

    public void setTitleView(String title){
        mTitleView.setText(title);
    }

    public void setBackClickListener(View.OnClickListener listener){
        mBackView.setOnClickListener(listener);
    }

    public ImageView getBackView(){
        return mBackView;
    }
}
