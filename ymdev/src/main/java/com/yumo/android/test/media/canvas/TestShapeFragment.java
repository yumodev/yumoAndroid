/**
 * TestShapeFragment.java
 * yumodev
 * 2014-12-2
 */
package com.yumo.android.test.media.canvas;

import java.util.ArrayList;
import java.util.List;

import com.yumo.android.R;
import com.yumo.android.test.media.custom.CircleProgressBar;
import com.yumo.android.test.media.custom.GradientProgressbar;
import com.yumo.android.test.media.custom.LayerView;
import com.yumo.android.test.media.custom.LinearGradientView;
import com.yumo.android.test.media.custom.RadarView;
import com.yumo.android.test.media.custom.RadialGradientView;
import com.yumo.android.test.media.custom.RoundFragment;
import com.yumo.android.test.media.custom.SelectorView;
import com.yumo.demo.view.YmTestFragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * yumodev
 * shape 样式详解
 */
public class TestShapeFragment extends YmTestFragment {
    private final String LOG_TAG = "TestShapeFragment";
    /**
     * 存放shape资源。
     */
    private List<Integer> mResList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRes();
    }

    private void initRes() {
        if (mResList == null){
            mResList = new ArrayList<>();
        }

        //边框圆角背景
        mResList.add(R.drawable.corners_bg);
        //渐变色
        mResList.add(R.drawable.shape_gradient);
        //描边
        mResList.add(R.drawable.shape_stoke);
        //圆形
        mResList.add(R.drawable.shape_type_oval);
    }

    public void testShowView(){
        LinearLayout linear = new LinearLayout(getContext());
        linear.setOrientation(LinearLayout.VERTICAL);
        for (Integer resId : mResList) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundResource(resId);

            LinearLayout.LayoutParams ltp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 40);
            ltp.topMargin = 10;
            ltp.leftMargin = 10;
            ltp.rightMargin = 10;
            linear.addView(linearLayout, ltp);
        }

        showTestView(linear);
    }

    public void testShowOvalView(){
        showResId(R.drawable.shape_type_oval1);
    }

    public void testShowRing(){
        showResId(R.drawable.shape_type_ring);
    }

    public void testShowOvalShadow(){
        showResId(R.drawable.shape_type_oval_shadow);
    }

    private void showResId(int resId){
        LinearLayout linear = new LinearLayout(getContext());
        linear.setBackgroundResource(R.color.transparent);
        linear.setPadding(0, 200, 0, 0);
        linear.setOrientation(LinearLayout.VERTICAL);

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundResource(resId);

        LinearLayout.LayoutParams ltp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linear.setGravity(Gravity.CENTER_HORIZONTAL);
        linear.addView(linearLayout, ltp);
        linear.setPadding(100,100,100,100);

        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(R.drawable.ic_launcher);
        linearLayout.addView(imageView);

        TextView textView = new TextView(getContext());
        textView.setId(android.R.id.message);
        textView.setText("testdddddd");
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextColor(getResources().getColor(R.color.red));

        linearLayout.addView(textView);

        Toast toast = new Toast(getContext());
        toast.setView(linear);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
    }

    public void testShowRadarView(){
        RadarView radarView = new RadarView(getContext());
        showTestView(radarView);
    }

    public void testGradientProgressBar(){
        GradientProgressbar gradientProgressbar = new GradientProgressbar(getContext());
        showTestView(gradientProgressbar);
    }

    public void testCircleprogressBar(){
        CircleProgressBar circleProgressBar = new CircleProgressBar(getContext());
        circleProgressBar.setMaxStepNum(100);
        circleProgressBar.update(80, 1);
        showTestView(circleProgressBar);
    }

    public void testLinearGradientView(){
        LinearGradientView linearGradientView = new LinearGradientView(getContext());
        showTestView(linearGradientView);
    }

    public void testRadialGradientView(){
        RadialGradientView radialGradientView = new RadialGradientView(getContext());
        showTestView(radialGradientView);
    }

    public void testLayerView(){
        showTestView(new LayerView(getContext()));
    }

    public void testSelectorView(){
        showTestView(new SelectorView(getContext()));
    }

    public void testRoundFragment(){
        showFragment(new RoundFragment());
    }
}
