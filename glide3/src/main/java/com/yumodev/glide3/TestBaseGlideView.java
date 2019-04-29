package com.yumodev.glide3;

import com.yumo.demo.view.YmTestFragment;
import com.yumodev.glide3.test.TestView1;

/**
 *
 * [ Android图片加载框架最全解析（一），Glide的基本用法](http://blog.csdn.net/guolin_blog/article/details/53759439)
 * Created by yumodev on 18/1/
 * Glide
 *
 * with方法, 创建一个加载图片的实例，可以传入Activity, FragmentActivity, Fragment, Context。
 *
 * RequestManager：
 *
 * load: 指定待加载的图片资源，本地图片，网络图片，Gif，资源，二进制流，Uri对象
 *
 * DrawableTypeRequest
 *
 * placeholder: 加载站位图。
 * error:错误
 * asBitmap:已图片的方式加载。
 * asGif:以Gif动画的方式加载。
 *
 * Glide默认会根据ImageView的大小加载图片，所以不存在内存浪费的问题。可以通过override方制定加载的图片大小。
 *
 */

public class TestBaseGlideView extends YmTestFragment {

    public void testTest(){
        showToastMessage("test");
    }

    public void testBase1(){
        TestView1 view = new TestView1(getContext());
        view.setUrl("http://img4.imgtn.bdimg.com/it/u=3025468710,1884922166&fm=27&gp=0.jpg");
        showTestView(view);
    }

    public void testBase2(){
        TestView1 view = new TestView1(getContext());
        view.setUrl("http://1img4.imgtn.bdimg.com/it/u=3025468710,1884922166&fm=27&gp=0.jpg");
        showTestView(view);
    }

    public void testBaseGif(){
        TestView1 view = new TestView1(getContext());
        view.setUrl("http://imgres.roboo.com/group6/M01/37/94/wKhkCliIEGKAd4fnACHb0jZtmF8546.gif");
        showTestView(view);
    }
}
