package com.yumo.android.test.view.flip;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

public class FocusedTextView extends androidx.appcompat.widget.AppCompatTextView {

    public FocusedTextView(Context context) {
        super(context);
        initView();
    }

    public FocusedTextView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
