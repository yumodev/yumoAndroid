package com.yumo.ui.web;

import android.content.Context;

/**
 * Created by yumodev on 17/11/22.
 * 创建网页的工具类
 */

public interface IBrowserFactory {
    public IBrowserView createPageView(Context context, String url);
    public IBrowserView getHomeView();
}
