package com.yumo.android.common.widget;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by yumodev on 15/9/21.
 */
public class YmViewPager extends ViewPager {

    public YmViewPager(Context context) {
        super(context);
    }

    public YmViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public class ViewPagerAdapter extends PagerAdapter {
        Object[] mData;

        public ViewPagerAdapter(Object[] data) {
            mData = data;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView((View) mData[position]);
            return mData[position];
        }

        @Override
        public int getCount() {
            return mData.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
        }
    }

    public class YmFragmentPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentsList;

        public YmFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public YmFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragmentsList = fragments;
        }

        @Override
        public int getCount() {
            return fragmentsList.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentsList.get(arg0);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

    }
}
