package com.yumo.android.test.media.canvas.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

/**
 * Created by yumodev on 17/12/22.
 *
 * 绘制弧形和扇形
 * drawArc(left,top,right,bottom,startAngle, sweepAngle, useCenter)
 * startAngle是弧形的起始角度
 * sweepAngle 弧形划过的角度
 * userCenter:表示是否连接到原型。不连接到圆心是弧形，连接到圆心是扇形
 *
 *
 * 弧度以x轴右侧为0度，按照顺时针方向画。
 */


public class TestArc1View extends View {

    private Paint mPant1;
    public TestArc1View(Context context){
        super(context);
        init();
    }

    private void init(){
        mPant1 = new Paint();
        //设置绘制颜色
        mPant1.setColor(Color.RED);
        //设置填充类型
        mPant1.setStyle(Paint.Style.STROKE);
        //设置边框宽度
        mPant1.setStrokeWidth(5);
        mPant1.setDither(true);
        mPant1.setAntiAlias(true);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        draw1(canvas);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void draw1(Canvas  canvas){
        canvas.drawArc(0,0,100,100, 135, 270, false, mPant1);
    }


}
