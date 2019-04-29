package com.yumodev.glide3.test;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yumodev.glide3.R;

/**
 * Created by yumodev on 18/1/17.
 */

public class TestView2 extends LinearLayout {

    private ImageView mImageView;
    private String mSrcUrl;
    public TestView2(Context context) {
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
                Glide.with(getContext())//指定Context，和生命周期有关
                        .load(mSrcUrl)//指定图片的rul
                        .placeholder(R.mipmap.ic_launcher_round)//展位图
                        .error(R.drawable.error)//错误图
                        .override(300, 300)//指定图片的尺寸。
                        .fitCenter()//指定图片的缩放类型。
                        .centerCrop()//图片缩放,有可能不被完全显示。
                        .skipMemoryCache(true)//跳过内存缓存。
                        .diskCacheStrategy(DiskCacheStrategy.NONE)//不使用磁盘缓存。
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)//只缓存原来的全分辨的图片。
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)//紧紧缓存最终图像。
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有版本的图像
                        .priority(Priority.HIGH)//指定优先级。
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
