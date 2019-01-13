package com.yumo.android.test.media.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yumodev on 15/7/30.
 */
public class RadialGradientView extends View{

    private Shader mRadialGradientView = null;
    private Paint mPaint = null;

    private int mStartColor = Color.parseColor("#FF61bdff");
    private int mEndColor = Color.parseColor("#00FFFFFF");

    public RadialGradientView(Context context) {
        super(context);

        mRadialGradientView = new RadialGradient(500,500,50, new int[]{mStartColor, mEndColor},null , Shader.TileMode.REPEAT);

        mPaint = new Paint();
    }

    public RadialGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setShader(mRadialGradientView);

        canvas.drawCircle(500,500,50,mPaint);
    }
}
