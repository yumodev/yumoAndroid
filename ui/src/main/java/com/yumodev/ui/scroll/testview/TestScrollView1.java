package com.yumodev.ui.scroll.testview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.yumodev.ui.R;

/**
 * Created by yumodev on 17/12/19.
 *
 * * [Android Scroller完全解析，关于Scroller你所需知道的一切](http://blog.csdn.net/guolin_blog/article/details/48719871)
 *
 * ScrollTo 让View相对于初始位置滚动某段距离，
 * 由于View的初始位置是不变的，因此不管我们点击多少次ScrollTO按钮滚动到统一个位置
 * ScrollBy 是让View相对于当前位置滚动某段距离
 * 那每当我们点击一次ScrollBy按钮，当前的View的位置都进行了变动。
 */

public class TestScrollView1 extends LinearLayout {
    private final String LOG_TAG = "TestScrollView1";

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    public TestScrollView1(Context context) {
        super(context);
        setupUI();
    }

    public TestScrollView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public TestScrollView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupUI();
    }


    private void setupUI(){
        setBackgroundResource(R.color.blue);
        setOrientation(LinearLayout.VERTICAL);

        mButton1 = createNewBtn("ScrollTo", new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                scrollTo(100, 100);
            }
        });
        addView(mButton1);

        mButton2 = createNewBtn("scrollBy", new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                scrollBy(100,100);
            }
        });

        addView(mButton2);

        mButton3 = createNewBtn("scrollByParent", new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });

        addView(mButton3);

    }

    private Button createNewBtn(String text, View.OnClickListener listener){
        Button btn = new Button(getContext());
        btn.setText(text);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity= Gravity.CENTER_HORIZONTAL;
        int margin = getContext().getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin);
        lp.setMargins(margin, margin, margin, margin);
        btn.setLayoutParams(lp);
        btn.setOnClickListener(listener);
        return btn;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.i(LOG_TAG, "compuleScroll");
    }
}
