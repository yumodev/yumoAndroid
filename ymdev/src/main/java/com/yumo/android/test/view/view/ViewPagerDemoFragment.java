/**
 * ViewPagerDemoFragment.java
 * yumo
 * 2015-2-3
 */
package com.yumo.android.test.view.view;

import java.util.ArrayList;
import java.util.List;

import com.yumo.android.R;
import com.yumo.android.test.activity.fragment.FragmentDemo;
import com.yumo.ui.viewpager.YmViewPager;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * yumo
 */
public class ViewPagerDemoFragment extends Fragment {
    private final String TAG = "ViewPageDemoFragment";

    /**
     * 普通pageradapter view
     */
    private final String TYPE_PAGER_ADAPTER = "pagerAdapter";

    /**
     * 普通pageradapter Fragment
     */
    private final String TYPE_FRAGMENT_ADAPTER = "pagerFragmentAdapter";

    /**
     * 一个微信demo，用Pager实现
     */
    private final String TYPE_PAGER_ADAPTER_WEIXIN = "pagerAdapterWeixin";

    /**
     * viewPager动画的练习
     */
    private final String TYPE_PAGER_ANIMATION = "viewPagerAnimation";

    private String[] mTypePagers = {TYPE_PAGER_ADAPTER, TYPE_FRAGMENT_ADAPTER, TYPE_PAGER_ADAPTER_WEIXIN, TYPE_PAGER_ANIMATION};

    private YmViewPager mViewPager = null;
    private Spinner mSpinner = null;
    private PagerTabStrip mTabStrip = null;
    private LinearLayout mBottomLinear = null;

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
    private List<String> mListTitles = new ArrayList<String>();
    private List<Fragment> mListFragments = new ArrayList<Fragment>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.viewpager_demo_page, null);
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

        mViewPager = getView().findViewById(R.id.viewpager);
        mViewPager.setCanScroll(false);
        mTabStrip = (PagerTabStrip) getView().findViewById(R.id.tabstrip);
        mBottomLinear = (LinearLayout) getView().findViewById(R.id.bottom);

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

        initSpinner();
        return true;
    }

    private void initWeixinClick() {
        mTabWeixin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
                resetImgWeixin();
                mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
            }
        });

        mTabFind.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
                resetImgWeixin();
                mImgFind.setImageResource(R.drawable.tab_find_frd_pressed);
            }
        });

        mTabAddress.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
                resetImgWeixin();
                mImgAddress.setImageResource(R.drawable.tab_address_pressed);
            }
        });

        mTabSetting.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(3);
                resetImgWeixin();
                mImgSetting.setImageResource(R.drawable.tab_settings_pressed);
            }
        });
    }

    private void resetImgWeixin() {
        mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
        mImgFind.setImageResource(R.drawable.tab_find_frd_normal);
        mImgAddress.setImageResource(R.drawable.tab_address_normal);
        mImgSetting.setImageResource(R.drawable.tab_settings_normal);
    }

    private void initSpinner() {
        mSpinner = (Spinner) getView().findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, mTypePagers);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String strType = mTypePagers[position];
                Log.d(TAG, "spinner onTemSelect position:" + position + " " + strType);
                mTabStrip.setVisibility(View.GONE);
                mBottomLinear.setVisibility(View.GONE);
                if (strType.equals(TYPE_PAGER_ADAPTER)) {
                    initPagerAdapter();
                } else if (strType.equals(TYPE_FRAGMENT_ADAPTER)) {
                    initFragmentAdapter();
                } else if (strType.equals(TYPE_PAGER_ADAPTER_WEIXIN)) {
                    initPagerAdapterWeixin();
                } else if (strType.equals(TYPE_PAGER_ANIMATION)) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initPagerAdapter() {
        mListViews.clear();
        mListTitles.clear();
        for (int n = 0; n < 4; n++) {
            TextView view = new TextView(getContext());
            view.setText("view:" + n);
            mListViews.add(view);

            mListTitles.add("tab :" + n);
        }

        mTabStrip.setBackgroundResource(R.color.yellow);
        mTabStrip.setTextColor(Color.RED);
        mTabStrip.setTabIndicatorColor(Color.GREEN);

        MyPagerAdapter adapter = new MyPagerAdapter();
        mViewPager.setAdapter(adapter);
    }

    private void initFragmentAdapter() {
        mTabStrip.setVisibility(View.VISIBLE);
        mListFragments.clear();
        mListTitles.clear();
        for (int n = 0; n < 4; n++) {
            String strText = "Fragment:" + n;
            FragmentDemo fragment = new FragmentDemo();
            fragment.setTestText(strText);

            mListFragments.add(fragment);
            mListTitles.add("tab :" + n);
        }

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getFragmentManager());
        adapter.setFragments(mListFragments);
        adapter.setTitles(mListTitles);

        mViewPager.setAdapter(adapter);

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }


    /**
     * TODO 测试微信demo
     * yumo
     * void
     * 2015-2-5
     */
    private void initPagerAdapterWeixin() {
        resetImgWeixin();
        mBottomLinear.setVisibility(View.VISIBLE);

        mListViews.clear();
        for (int n = 0; n < 4; n++) {
            TextView view = new TextView(getContext());
            view.setText("view:" + n);
            mListViews.add(view);
        }

        MyPagerAdapter adapter = new MyPagerAdapter();
        mViewPager.setAdapter(adapter);

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int pos) {
                Log.d(TAG, "onPageSelect:pos");
                switch (pos) {
                    case 0: {
                        resetImgWeixin();
                        mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
                        break;
                    }
                    case 2: {
                        resetImgWeixin();
                        mImgFind.setImageResource(R.drawable.tab_find_frd_normal);
                        break;
                    }
                    case 1: {
                        resetImgWeixin();
                        mImgAddress.setImageResource(R.drawable.tab_address_pressed);
                        break;
                    }
                    case 3: {
                        resetImgWeixin();
                        mImgSetting.setImageResource(R.drawable.tab_settings_pressed);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void initViewPagerAnimation() {

    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return super.instantiateItem(container, position);
            Log.d(TAG, "instantigateItem:" + position);
            container.addView(mListViews.get(position));
            return mListViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            Log.d(TAG, "destroyItem:" + position);
            container.removeView(mListViews.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return super.getPageTitle(position);
            Log.d(TAG, "getPageTitle:" + position);
            return mListTitles.get(position);
        }

    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<String> mTitles = new ArrayList<String>();
        private List<Fragment> mFragments = new ArrayList<Fragment>();


        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return mFragments.get(arg0);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return super.getPageTitle(position);
            return mTitles.get(position);
        }

        public void setFragments(List<Fragment> fragments) {
            this.mFragments = fragments;
        }

        public void setTitles(List<String> titles) {
            this.mTitles = titles;
        }

    }

}
