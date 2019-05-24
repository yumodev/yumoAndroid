/**
 * TabFragmentWeiXin.java
 * yumo
 * 2015-2-5
 */
package com.yumo.android.test.view.view;

import java.util.ArrayList;
import java.util.List;

import com.yumo.android.R;
import com.yumo.android.test.activity.fragment.FragmentDemo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * yumo
 */
public class TabFragmentWeiXin extends Fragment {
    private final String TAG = "TabFragmentWeiXin";

    private LinearLayout mBottomLinear = null;
    private Fragment mFragment = null;

    //weixin
    private LinearLayout mTabWeixin = null;
    private LinearLayout mTabFind = null;
    private LinearLayout mTabAddress = null;
    private LinearLayout mTabSetting = null;

    private ImageView mImgWeixin = null;
    private ImageView mImgFind = null;
    private ImageView mImgAddress = null;
    private ImageView mImgSetting = null;


    private List<View> mListViews = new ArrayList<View>();
    private List<Fragment> mListFragments = new ArrayList<Fragment>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_demo_page, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    /**
     * TODO 初始化界面
     * yumo
     *
     * @return boolean
     * 2014-12-3
     */
    private boolean initView() {
        initFragments();

        mBottomLinear = (LinearLayout) getView().findViewById(R.id.bottom);
        //mFragment = (android.support.v4.app.Fragment) findViewById(R.id.fragment);

        //weixin
        mTabWeixin = (LinearLayout) getView().findViewById(R.id.tab_weixin);
        mTabFind = (LinearLayout) getView().findViewById(R.id.tab_weixin_find);
        mTabAddress = (LinearLayout) getView().findViewById(R.id.tab_address);
        mTabSetting = (LinearLayout) getView().findViewById(R.id.tab_settings);

        mImgWeixin = (ImageView) getView().findViewById(R.id.tab_weixin_img);
        mImgFind = (ImageView) getView().findViewById(R.id.tab_weixin_find_img);
        mImgAddress = (ImageView) getView().findViewById(R.id.tab_weixin_address_img);
        mImgSetting = (ImageView) getView().findViewById(R.id.tab_setting_img);
        initWeixinClick();

        selectFragment(0);
        return true;
    }

    private void initFragments() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mListFragments.clear();
        for (int n = 0; n < 4; n++) {
            String strText = "Fragment:" + n;
            FragmentDemo fragment = new FragmentDemo();
            fragment.setTestText(strText);

            mListFragments.add(fragment);
            transaction.add(R.id.fragment, fragment);
        }

        transaction.commit();
    }


    private void initWeixinClick() {
        mTabWeixin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectFragment(0);
            }
        });

        mTabFind.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectFragment(2);
            }
        });

        mTabAddress.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectFragment(1);
            }
        });

        mTabSetting.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectFragment(3);
            }
        });
    }

    private void resetImgWeixin() {
        mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
        mImgFind.setImageResource(R.drawable.tab_find_frd_normal);
        mImgAddress.setImageResource(R.drawable.tab_address_normal);
        mImgSetting.setImageResource(R.drawable.tab_settings_normal);
    }

    private void selectFragment(int pos) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        for (int n = 0; n < mListFragments.size(); n++) {
            transaction.hide(mListFragments.get(n));
        }

        resetImgWeixin();

        switch (pos) {
            case 0: {
                mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
                break;
            }
            case 2: {
                mImgFind.setImageResource(R.drawable.tab_find_frd_pressed);
                break;
            }
            case 1: {
                mImgAddress.setImageResource(R.drawable.tab_address_pressed);
                break;
            }
            case 3: {
                mImgSetting.setImageResource(R.drawable.tab_settings_pressed);
                break;
            }
        }

        transaction.show(mListFragments.get(pos));
        transaction.commit();

    }


}