package com.yumo.android.test.media.canvas.test;

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
 * 辐射渐变，从中心向周围辐射的渐变。
 * RadialGradient(float centerX, float centerY, float radius, int centerColor, int edgeColor, TileMode tileMode
 *
 * centerX, centerY：辐射中心坐标
 * radius:辐射半径
 * edgeColor:辐射边缘颜色
 * tileMode：辐射之外的找色模式。
 */
public class RadialGradientView extends View{

    private Shader mRadialGradientView = null;
    private Paint mPaint = null;

    private int mStartColor = Color.RED;
    private int mEndColor = Color.BLUE;

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

        RadialGradient radialGradient = new RadialGradient(500f, 500f, 100f, mStartColor, mEndColor, Shader.TileMode.CLAMP);
        mPaint.setShader(radialGradient);
        canvas.drawCircle(500,500,100f,mPaint);


        RadialGradient radialGradient1 = new RadialGradient(500f, 500f, 100f, mStartColor, mEndColor, Shader.TileMode.REPEAT);
        mPaint.setShader(radialGradient1);
        canvas.drawCircle(500,800,100f,mPaint);


        RadialGradient radialGradient2 = new RadialGradient(500f, 500f, 100f, mStartColor, mEndColor, Shader.TileMode.MIRROR);
        mPaint.setShader(radialGradient2);
        canvas.drawCircle(500,1100,100f,mPaint);

        radialGradient1(canvas);
    }

    private void radialGradient1(Canvas canvas){
        RadialGradient radialGradient = new RadialGradient(500f, 300f, 100f, Color.parseColor("#ff00C4CB"), Color.parseColor("#00000000"), Shader.TileMode.CLAMP);


        Paint paint = new Paint();
        //设置绘制颜色
        //paint.setColor(Color.TRANSPARENT);
        //设置填充类型
        paint.setStyle(Paint.Style.FILL);
        //设置边框宽度
        paint.setStrokeWidth(0);

        paint.setAntiAlias(true);

        paint.setShader(radialGradient);
        canvas.drawCircle(500,300,100f, paint);
    }
}
