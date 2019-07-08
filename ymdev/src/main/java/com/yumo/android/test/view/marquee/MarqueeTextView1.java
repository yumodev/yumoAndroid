package com.yumo.android.test.view.marquee;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class MarqueeTextView1 extends AppCompatTextView {

    public MarqueeTextView1(Context context) {
        super(context);
    }

    public MarqueeTextView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        //return super.isFocused();
        return true;
    }
}