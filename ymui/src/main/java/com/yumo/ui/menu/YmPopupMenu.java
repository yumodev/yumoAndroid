package com.yumo.ui.menu;

import android.content.Context;
import android.support.annotation.MenuRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yumo.common.android.YmResUtil;

/**
 * Created by yumodev on 17/3/14.
 * 一个弹出菜单
 * 支持任意位置显示
 * 底部列表显示
 */
public class YmPopupMenu extends PopupWindow implements View.OnClickListener{
    private Context mContext;

    private YmMenu mMenu = new YmContextMenu();
    /**
     * Menu背景颜色
     */
    private int mMenuBgResId;
    /**
     * Menu的每项布局。
     */
    private int mItemLayoutResId;

    /**
     * Menu的父容器
     */
    private LinearLayout mMenuContainer = null;

    /**
     * Menu的监听事件
     */
    private YmMenuListener mListener = null;

    /**
     * 初始化PopMenu
     * @param context
     */
    public YmPopupMenu(Context context){
        mContext = context;
        mMenuBgResId = android.R.color.white;
        create();
    }

    /**
     * 创建Popmenu
     * @return
     */
    private YmPopupMenu create(){
        ScrollView view = new ScrollView(mContext);
        int margin = (int)YmResUtil.dipToPx(mContext, 16);
        view.setPadding(margin, margin, margin, margin);
        setContentView(view);

        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(mContext.getResources().getDrawable(android.R.color.transparent));
        setTouchable(true);
        setFocusable(true);
        setOutsideTouchable(true);

        mMenuContainer = new LinearLayout(mContext);
        mMenuContainer.setOrientation(LinearLayout.VERTICAL);
        mMenuContainer.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.color.white));
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        int margin = (int)YmResUtil.dipToPx(mContext, 16);
//        lp.setMargins(margin, margin, margin, margin);
        view.addView(mMenuContainer);
        return this;
    }

    /**
     * 设置监听器
     * @param listener
     */
    public void setListener(YmMenuListener listener){
        mListener = listener;
    }

    /**
     * 在create前调用
     * @param resId
     * @return
     */
    public YmPopupMenu setBgResId(int resId){
        mMenuBgResId = resId;
        setBackgroundDrawable(mContext.getResources().getDrawable(mMenuBgResId));
        return this;
    }

    public YmPopupMenu setItemLayoutResId(int resId){
        mItemLayoutResId = resId;
        return this;
    }


    public YmPopupMenu addItemView(YmMenuItem item){
        View view = item.getmItemView();
        view = view != null?view:createDefaultItemView();

        view.setTag(item);
        view.setOnClickListener(this);

        if (!TextUtils.isEmpty(item.getTitle())){
            TextView titleView = view.findViewById(android.R.id.title);
            titleView.setText(item.getTitle());
        }

        ImageView imageView = view.findViewById(android.R.id.icon);
        if (item.getIcon() != null){
            imageView.setImageDrawable(item.getIcon());
        }else{
            imageView.setVisibility(View.GONE);
        }

        mMenuContainer.addView(view);
        return this;
    }

    public YmPopupMenu addMenu(int menuId, String title){
        YmMenuItem item = new YmMenuItem(menuId, title, null);
        return addItemView(item);
    }

    /**
     * 添加Menu
     * @param menuId
     * @param title
     * @param iconResId
     * @return
     */
    public YmPopupMenu addMenu(int menuId, String title, int iconResId){
        YmMenuItem item = new YmMenuItem(menuId, title, mContext.getResources().getDrawable(iconResId));
        return addItemView(item);
    }

    public YmPopupMenu addMenu(int menuId, String title, int iconResId, View itemView){
        YmMenuItem item = new YmMenuItem(menuId, title, mContext.getResources().getDrawable(iconResId), itemView);
        return addItemView(item);
    }

    /**
     * 创建的默认的每项菜单View
     * @return
     */
    private View createDefaultItemView(){
        return View.inflate(mContext, mItemLayoutResId, null);
    }

    @Override
    public void onClick(View view) {
        if (mListener != null){
            mListener.onItemClickListener((YmMenuItem) view.getTag(), view);
        }
    }


    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        if (mListener != null){
            mListener.showMenu();
        }
    }

    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        if (mListener != null){
            mListener.showMenu();
        }
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        if (mListener != null){
            mListener.showMenu();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mListener != null){
            mListener.dismiss();
        }
    }

    public void addMenu(@MenuRes int menuResId){
        YmMenu menu = new YmContextMenu();
        YmMenuHelper.inflateMenuRes(mContext, menuResId, menu);

        for (int i = 0; i < menu.size();i++) {
            if (mMenu.findItem(menu.getItem(i).getItemId()) != null){
                continue;
            }
            YmMenuItem item = menu.getItem(i);
            addItemView(item);
        }
    }

    /**
     * Created by yumodev on 17/3/15.
     */

    public interface YmMenuListener {

        /**
         * 菜单显示
         */
        void showMenu();

        /**
         * 菜单隐藏
         */
        void dismiss();

        /**
         * 点击事件
         * @param menuItem
         * @param view 被点击的View
         */
        void onItemClickListener(YmMenuItem menuItem, View view);
    }
}
