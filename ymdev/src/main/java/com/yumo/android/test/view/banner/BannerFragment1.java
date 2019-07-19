package com.yumo.android.test.view.banner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.yumo.android.R;
import com.yumo.android.test.ui.util.YmViewUtils;
import com.yumo.common.log.Log;
import java.util.ArrayList;

public class BannerFragment1 extends Fragment {
  private final String LOG_TAG = "BannerFragment";
  private ViewPager viewPager;
  private int[] imageResIds;
  private ArrayList<ImageView> imageViewList;

  private LinearLayout ll_point_container;
  private int previousSelectedPosition = 0;
  boolean isRunning = false;

  boolean mViewPagerClipChildren = true;
  int mViewPagerPageMargin = YmViewUtils.dip2px(20);
  int mViewPagerMargin = YmViewUtils.dip2px(30);

  int mRealSize = 2;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = View.inflate(getContext(), R.layout.banner1_page, null);
    init(view);
    return view;
  }

  private void init(View view){
    // 初始化布局 View视图
    initViews(view);

    // Model数据
    initData();

    // Controller 控制器
    initAdapter();

    // 开启轮询
    new Thread() {
      public void run() {
        isRunning = true;
        while (isRunning) {
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          // 往下跳一位
          getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
              System.out.println("设置当前位置: " + viewPager.getCurrentItem());
              viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
          });
        }
      }
    }.start();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    isRunning = false;
  }

  private void initViews(View view) {
    viewPager = (ViewPager) view.findViewById(R.id.viewpager);
    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override public void onPageSelected(int position) {
        // 新的条目被选中时调用
        Log.i(LOG_TAG, "onPageSelected: " + position);
        int newPosition = position % imageViewList.size();


        //		for (int i = 0; i < ll_point_container.getChildCount(); i++) {
        //			View childAt = ll_point_container.getChildAt(position);
        //			childAt.setEnabled(position == i);
        //		}
        // 把之前的禁用, 把最新的启用, 更新指示器
        ll_point_container.getChildAt(previousSelectedPosition).setBackgroundResource(R.drawable.banner_point_normal);
        ll_point_container.getChildAt(newPosition).setBackgroundResource(R.drawable.banner_point_select);

        // 记录之前的位置
        previousSelectedPosition = newPosition;
      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });

   	//viewPager.setOffscreenPageLimit(2);// 左右各保留几个对象
    ll_point_container = (LinearLayout) view.findViewById(R.id.ll_point_container);

    //if (mViewPagerClipChildren){
    //  viewPager.setPageMargin(mViewPagerPageMargin);
    //  viewPager.setClipChildren(false);
    //  ViewGroup rlBannnerView = view.findViewById(R.id.rl_banner);
    //  rlBannnerView.setClipChildren(false);
    //  ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) viewPager.getLayoutParams();
    //  lp.setMargins(mViewPagerMargin, 0, mViewPagerMargin, 0);
    //  viewPager.setLayoutParams(lp);
    //
    //  rlBannnerView.setOnTouchListener(new View.OnTouchListener() {
    //    @SuppressLint("ClickableViewAccessibility")
    //    @Override
    //    public boolean onTouch(View v, MotionEvent event) {
    //      return viewPager.dispatchTouchEvent(event);
    //    }
    //  });
    //}
  }

  /**
   * 初始化要显示的数据
   */
  private void initData() {
    // 图片资源id数组
    //imageResIds = new int[]{R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4, R.drawable.banner5};
    imageResIds = new int[]{R.drawable.banner1, R.drawable.banner2,R.drawable.banner1};
    if (imageResIds.length <=2){
      //viewPager.setClipChildren(true);
    }

    // 初始化要展示的5个ImageView
    imageViewList = new ArrayList<ImageView>();

    ImageView imageView;
    View pointView;
    LinearLayout.LayoutParams layoutParams;
    for (int i = 0; i < imageResIds.length; i++) {
      // 初始化要显示的图片对象
      imageView = new ImageView(getContext());
      imageView.setBackgroundResource(imageResIds[i]);
      imageViewList.add(imageView);

      pointView = new View(getContext());
      pointView.setBackgroundResource(R.drawable.banner_point_normal);
      layoutParams = new LinearLayout.LayoutParams(5, 5);
      if (i != 0){
        layoutParams.leftMargin = 10;
      }
      ll_point_container.addView(pointView, layoutParams);
    }
  }

  private void initAdapter() {
    ll_point_container.getChildAt(0).setEnabled(true);
    previousSelectedPosition = 0;

    // 设置适配器
    viewPager.setAdapter(new MyAdapter());
  }

  class MyAdapter extends PagerAdapter {

    @Override
    public int getCount() {
      if (imageViewList == null || imageViewList.isEmpty()){
        return 0;
      }

      if (imageViewList.size() == 1){
        return imageViewList.size();
      }
      return Integer.MAX_VALUE;
    }

    // 3. 指定复用的判断逻辑, 固定写法
    @Override
    public boolean isViewFromObject(View view, Object object) {
      //			System.out.println("isViewFromObject: "+(view == object));
      // 当划到新的条目, 又返回来, view是否可以被复用.
      // 返回判断规则
      return view == object;
    }

    // 1. 返回要显示的条目内容, 创建条目
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      int newPosition = position % mRealSize;
      Log.i(LOG_TAG, "instantiateItem初始化: " + position +" "+newPosition);

     View view = imageViewList.get(newPosition);
      if (container.equals(view.getParent())) {
        container.removeView(view);
      }

      container.addView(view);

      return view; // 必须重写, 否则报异常
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      // object 要销毁的对象
      Log.i(LOG_TAG, "destroyItem销毁: " + position);
      container.removeView((View) object);
    }

    //@Override public float getPageWidth(int position) {
    //  return 0.88f;
    //}
  }
}
