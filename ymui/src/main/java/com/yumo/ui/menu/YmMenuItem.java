package com.yumo.ui.menu;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by yumodev on 17/11/25.
 * 菜单的每一项数据
 */

public class YmMenuItem {
    private int mItemId;
    private String mTitle;
    private boolean mEnable = true;
    private boolean mIsVisible = true;
    private Drawable mIconDrawable = null;

    public View getmItemView() {
        return mItemView;
    }

    public void setmItemView(View mItemView) {
        this.mItemView = mItemView;
    }

    private View mItemView = null;

    public YmMenuItem(){

    }

    public YmMenuItem(int itemId, String title, Drawable iconDrawable){
        mItemId = itemId;
        mTitle = title;
        mIconDrawable = iconDrawable;
    }

    public YmMenuItem(int itemId, String title, Drawable iconDrawable, View itemView){
        mItemId = itemId;
        mTitle = title;
        mIconDrawable = iconDrawable;
        mItemView = itemView;
    }

    public YmMenuItem setItemId(int itemId){
        mItemId = itemId;
        return this;
    }
    public int getItemId(){
        return mItemId;
    }

    public YmMenuItem setTitle(String title){
        mTitle = title.toString();
        return this;
    }

    public String getTitle(){
        return mTitle;
    }


    public YmMenuItem setIcon(Drawable icon){
        mIconDrawable = icon;
        return this;
    }

    public Drawable getIcon(){
        return mIconDrawable;
    }

    public YmMenuItem setVisible(boolean visible){
        mIsVisible = visible;
        return this;
    }

    public boolean isVisible(){
        return mIsVisible;
    }

    public YmMenuItem setEnabled(boolean enabled){
        mEnable = enabled;
        return this;
    }


    public boolean isEnabled(){
        return mEnable;
    }

}
