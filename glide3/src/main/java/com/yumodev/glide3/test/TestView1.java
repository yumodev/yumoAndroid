package com.yumodev.glide3.test;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.yumodev.glide3.R;

/**
 * Created by yumodev on 18/1/17.
 */

public class TestView1 extends LinearLayout {

    private ImageView mImageView;
    private String mSrcUrl;
    public TestView1(Context context) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        initView();
    }

    private void initView(){
        addBtn();
        addImgView();
    }

    private void addBtn(){
        Button button = new Button(getContext());
        button.setText("Load Image");

        addView(button);
        button.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Glide.with(getContext())
                        .load(mSrcUrl)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.drawable.error)
                        .into(mImageView);
            }
        });
    }

    private void addImgView(){
        mImageView = new ImageView(getContext());
        addView(mImageView);
    }

    public void setUrl(String url){
        mSrcUrl = url;
    }
}
