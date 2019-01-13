package com.yumo.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumodev.ui.R;

/**
 * TODO: document your custom view class.
 */
public class YmImageTextView extends FrameLayout {
    private String mText;
    private int mTextColor = Color.RED;
    private float mTextMargin = 0;
    private Drawable mImageSrc;

    private TextPaint mTextPaint;

    private ImageView mImageView = null;
    private TextView mTextView = null;
    public YmImageTextView(Context context) {
        super(context);
        init(null, 0);
    }

    public YmImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public YmImageTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.YmImageTextView, defStyle, 0);

        mText = a.getString(
                R.styleable.YmImageTextView_text);
        mTextColor = a.getColor(
                R.styleable.YmImageTextView_text_color,
                mTextColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mTextMargin = a.getDimension(
                R.styleable.YmImageTextView_text_margin,
                mTextMargin);

        if (a.hasValue(R.styleable.YmImageTextView_image_src)) {
            mImageSrc = a.getDrawable(
                    R.styleable.YmImageTextView_image_src);
            mImageSrc.setCallback(this);
        }

        a.recycle();
    }



}
