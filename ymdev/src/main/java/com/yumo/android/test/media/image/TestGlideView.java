package com.yumo.android.test.media.image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.yumo.android.MainActivity;
import com.yumo.common.net.YmOkHttpUtil;
import com.yumo.demo.view.YmTestFragment;

import java.io.InputStream;

/**
 * Created by yumodev on 16/10/31.
 * 测试Glide的使用
 */

public class TestGlideView extends YmTestFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.get(getActivity())
                .register(          //使用okhttp作为图片请求
                        GlideUrl.class
                        ,InputStream.class
                        ,new OkHttpUrlLoader.Factory(YmOkHttpUtil.getOkHttpClient()));
    }

    public void testView(){
        ImageView targetImageView = new ImageView(getContext());
        String internetUrl = "http://i.imgur.com/DvpvklR.png";

        Glide.with(getContext())
                .load(internetUrl)
                .skipMemoryCache(true)   //验证码不缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(targetImageView);
        showTestView(targetImageView);
    }

    public void testImageView1(){
        ImageView imageView = new ImageView(getContext());
       // String internetUrl = "http://www.zebrai.cn/login/picture/getCaptcha";/
        String internetUrl = "http://www.52banma.com/login/picture/getCaptcha";
        //String internetUrl = "http://test.icongtai.com:8088/login/picture/getCaptcha";
        //String internetUrl = "http://opentest.icongtai.com/login/picture/getCaptcha";


        Glide.with(getActivity())
                .load(internetUrl)
                .skipMemoryCache(true)   //验证码不缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
        showTestView(imageView);
    }
}
