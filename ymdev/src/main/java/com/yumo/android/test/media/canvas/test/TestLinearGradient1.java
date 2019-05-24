package com.yumo.android.test.media.canvas.test;

import android.content.Context;
import android.graphics.Color;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yumo.android.R;
import com.yumo.common.android.YmResUtil;

public class TestLinearGradient1 extends LinearLayout {

    public TestLinearGradient1(Context context) {
        super(context);
        setOrientation(VERTICAL);
        setBackgroundColor(Color.parseColor("#ff070910"));

        setBackgroundColor(Color.TRANSPARENT);


        addSpace();
        addView();
        addSpace();
        addView2();
    }

    private void addView2(){
        View space = new View(getContext());
        space.setBackgroundResource(R.drawable.linear_gradient1);
        addView(space, new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)YmResUtil.dipToPx(getContext(), 100)));

    }

    private void addSpace(){
        View space = new View(getContext());
        space.setBackgroundResource(R.color.transparent);
        addView(space, new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)YmResUtil.dipToPx(getContext(), 20)));
    }

    private void addView(){
        View space = new View(getContext());
        space.setBackgroundResource(R.drawable.linear_gradient2);
        addView(space, new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)YmResUtil.dipToPx(getContext(), 100)));
    }



}
