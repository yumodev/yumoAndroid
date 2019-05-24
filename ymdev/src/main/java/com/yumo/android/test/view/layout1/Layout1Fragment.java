package com.yumo.android.test.view.layout1;

import androidx.lifecycle.LifecycleRegistry;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yumo.android.R;
import com.yumo.android.test.activity.fragment.FragmentDemo;
import com.yumo.common.log.Log;
import com.yumo.common.util.Reflect;
import com.yumo.ui.arch.YmLifeCycle;
import com.yumo.ui.fragment.YmV4Fragment;
import com.yumo.ui.viewpager.YmViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Layout1Fragment extends YmV4Fragment {

    private Titleview mTitleView = new Titleview();
    private LayoutMenuNavView mMenuNavView = new LayoutMenuNavView();
    private InfoView mInfoView = new InfoView();
    private YmViewPager mViewPager = null;
    private List<Fragment> mListFragments = null;
    private int mLastOrientation;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLifecycle().addObserver(mTitleView);
        getLifecycle().addObserver(mMenuNavView);
        getLifecycle().addObserver(mInfoView);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLastOrientation = getResources().getConfiguration().orientation;
        View view = inflater.inflate(R.layout.layout1, null);
        mTitleView.setContentView(view.findViewById(R.id.layout_title_view));
        mMenuNavView.setContentView(view.findViewById(R.id.layout_nav_view));
        mInfoView.setContentView(view.findViewById(R.id.layout_info_view));
        initViewPager(view);
        changeOrientation(mLastOrientation);
        return view;
    }

    private void changeOrientation(int orientation){
        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            RelativeLayout.LayoutParams navLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            navLp.addRule(RelativeLayout.ABOVE, R.id.layout_info_view);
            mMenuNavView.getContentView().setLayoutParams(navLp);

            RelativeLayout.LayoutParams pagerLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            pagerLp.addRule(RelativeLayout.ABOVE,R.id.layout_nav_view);
            mViewPager.setLayoutParams(pagerLp);

            RelativeLayout.LayoutParams infoLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            infoLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,1);
            mInfoView.getContentView().setLayoutParams(infoLp);


        }else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            RelativeLayout.LayoutParams navLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            navLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
            //navLp.addRule(RelativeLayout.LEFT_OF, R.id.viewpager);
            mMenuNavView.getContentView().setLayoutParams(navLp);


            RelativeLayout.LayoutParams pagerLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            pagerLp.addRule(RelativeLayout.RIGHT_OF, R.id.layout_nav_view);
            pagerLp.addRule(RelativeLayout.LEFT_OF, R.id.layout_info_view);
            mViewPager.setLayoutParams(pagerLp);

            RelativeLayout.LayoutParams infoLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            infoLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,1);
            //pagerLp.addRule(RelativeLayout.RIGHT_OF, R.id.viewpager);

            mInfoView.getContentView().setLayoutParams(infoLp);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation != mLastOrientation){
            mLastOrientation = newConfig.orientation;
            changeOrientation(mLastOrientation);
        }

        LifecycleRegistry registry = (LifecycleRegistry) getLifecycle();

        Map observerMap =  (Map)(Reflect.on(registry).field("mObserverMap").get("mHashMap"));
        for (Object key : observerMap.keySet()){
            Log.i("TestLifeCycle", key.getClass().getName());
            if (key instanceof YmLifeCycle){
                YmLifeCycle.class.cast(key).onConfigurationChanged(newConfig);
            }
        }

    }

    private void initViewPager(View view){
        mViewPager = view.findViewById(R.id.viewpager);

        mListFragments = new ArrayList<>();
        for (int n = 0; n < 5; n++) {
            String strText = "Fragment:" + n;
            FragmentDemo fragment = new FragmentDemo();
            fragment.setTestText(strText);

            mListFragments.add(fragment);
        }

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getFragmentManager());
        adapter.setFragments(mListFragments);

        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mMenuNavView.addCheckListener(new LayoutMenuNavView.CheckListener() {
            @Override
            public void onPageChecked(int index) {
                mViewPager.setCurrentItem(index);
            }
        });

    }



    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

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
            return "";
        }

        public void setFragments(List<Fragment> fragments) {
            this.mFragments = fragments;
        }

    }
}
