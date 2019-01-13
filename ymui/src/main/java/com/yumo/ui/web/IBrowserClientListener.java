package com.yumo.ui.web;

import android.graphics.Bitmap;

/**
 * Created by yumodev on 16/12/6.
 * 网页变化的通知
 */

public interface IBrowserClientListener {
    void updateTitle(String title);
    void updateIcon(Bitmap bitmap);
    void updateProgress(int progress);
    void finishLoading();
    void setTopBarVisible(int visible, boolean animate);
    void tabChange();
}
