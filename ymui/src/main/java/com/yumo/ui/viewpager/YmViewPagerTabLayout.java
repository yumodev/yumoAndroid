package com.yumo.ui.viewpager;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wks on 15/11/19.
 */
public class YmViewPagerTabLayout {

    private final String LOG_TAG = "YmViewPagerTabLayout";

    private int mCount = 0;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout = null;

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private List<String> mTitleList = new ArrayList<String>();

    public YmViewPagerTabLayout() {
    }

    public void setTitleList(List<String> list){
        mTitleList = list;
    }

    public void setFragmentList(List<Fragment> fragmentList){
        mFragmentList = fragmentList;
    }

    public List<Fragment> getFragmentList(){
        return mFragmentList;
    }

    public void addItem(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mTitleList.add(title);
    }

    public void setCurrentItem(int pos){
        mViewPager.setCurrentItem(pos);
    }

    public Fragment getCurrentFragment(){
        return mFragmentList.get(mViewPager.getCurrentItem());
    }

    public void setCount(int count){
        mCount = count;
    }

    public void setupUI(ViewPager viewPager, TabLayout tabLayout, FragmentManager fragmentManager){
        mViewPager = viewPager;
        mViewPagerAdapter = new ViewPagerAdapter(fragmentManager);
        mViewPager.setOffscreenPageLimit(mCount);
        mViewPager.setAdapter(mViewPagerAdapter);


        mTabLayout = tabLayout;
        mTabLayout.setupWithViewPager(mViewPager);

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i(LOG_TAG, "getItem position=" + position);
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            Log.i(LOG_TAG, "getTabCount:"+mFragmentList.size());
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }
}
