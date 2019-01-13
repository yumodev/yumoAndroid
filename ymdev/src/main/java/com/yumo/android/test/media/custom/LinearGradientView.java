package com.yumo.android.test.media.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mac on 15/7/30.
 */
public class LinearGradientView extends View{

    private Paint mPaint = null;
    private Shader mLinearGradient = null;

    private int mStartColor = Color.parseColor("#FF61bdff");
    private int mEndColor = Color.parseColor("#00FFFFFF");
    public LinearGradientView(Context context) {
        super(context);

        mLinearGradient = new LinearGradient(0,0,0,100,new int[]{mStartColor, mEndColor}, null,Shader.TileMode.REPEAT);

        mPaint = new Paint();
    }

    public LinearGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setShader(mLinearGradient);
        canvas.drawRect(0, 0, 200, 100, mPaint);

        canvas.drawRect(400, 200, 500, 300, mPaint);
    }
}
