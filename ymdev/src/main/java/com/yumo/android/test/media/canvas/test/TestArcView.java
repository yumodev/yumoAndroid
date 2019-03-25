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


public class TestArcView extends View {

    public TestArcView(Context context){
        super(context);
        setBackgroundColor(Color.RED);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        //设置绘制颜色
        paint.setColor(Color.RED);
        //设置填充类型
        paint.setStyle(Paint.Style.STROKE);
        //设置边框宽度
        paint.setStrokeWidth(5);
        //画板背景。 定义白色画板背景
        canvas.drawColor(Color.RED);

        //昨上角绘制一个原型。

        canvas.drawArc(0,0,100,100, 90, 180, true, paint);

//            paint.setStyle(Paint.Style.FILL);
//            RectF rectF = new RectF(0, 200, 200, 300);
//            canvas.drawOval(rectF, paint);


        drawArc1(canvas);

        drawArc2(canvas);

    }

    /**
     * 画一个圆弧
     *
     *     <gradient android:type="linear" android:useLevel="true" android:startColor="#ff101010" android:endColor="#ff202020" android:angle="180" />
     */
    private void drawArc1(Canvas canvas){

        Paint paint = new Paint();
        //设置绘制颜色
        paint.setColor(Color.RED);
        //设置填充类型
        paint.setStyle(Paint.Style.FILL);
        //设置边框宽度
        paint.setStrokeWidth(5);

        paint.setAntiAlias(true);


        int width = getResources().getDisplayMetrics().widthPixels;
        LinearGradient linearGradient = new LinearGradient(0,0, 0,100,new int[]{0xff101010, 0xffffff00}, null, Shader.TileMode.REPEAT);

        paint.setShader(linearGradient);

        canvas.drawArc(0,100, width,200, 0, 180, false, paint);
    }


    private void drawArc2(Canvas canvas){
        Paint paint = new Paint();
        //设置绘制颜色
        paint.setColor(Color.RED);
        //设置填充类型

        //设置边框宽度
        paint.setStrokeWidth(5);

        paint.setAntiAlias(true);

        int top = -50;
        int height = 100;


        int width = getResources().getDisplayMetrics().widthPixels;
        LinearGradient linearGradient = new LinearGradient(0,0, 0,height,new int[]{0xff101010, 0xffffff00}, null, Shader.TileMode.REPEAT);

        paint.setShader(linearGradient);


        int layerId = canvas.saveLayer(0, top, width, top+height, null, Canvas.ALL_SAVE_FLAG);
        //正常绘制黄色的圆形
        canvas.drawRect(new RectF(0, top+height/2, width, top+height), paint);
        //使用CLEAR作为PorterDuffXfermode绘制蓝色的矩形
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawArc(new RectF(0,top, width,top+height), 0, 180, false, paint);
        //最后将画笔去除Xfermode
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);

    }
}
