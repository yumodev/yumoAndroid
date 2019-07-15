package com.yumo.android.test.media.canvas.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yumodev on 15/7/30.
 *
 * 当设置了 Shader 之后，Paint 在绘制图形和文字时就不使用  setColor/ARGB() 设置的颜色了，而是使用 Shader 的方案中的颜色。
 *
 * 渐变着色器
 *
 * TileMode:
 * CLAMP: 短点之外延续端点处的颜色
 * MIRROR: 镜像
 * REPEAT: 重复
 *
 * android:angle
 * Integer，代表渐变颜色的角度， 0 is left to right, 90 is bottom to top. 必须是45的整数倍. 
 * 默认是 0.该属性只有在type=linear情况下起作用，默认的type为linear。
 * 默认情况下，从左到右：
 *
 *                                                            
 *
 * xml代码：<gradient 
 *         android:startColor="#000000"
 *         android:endColor="#ffffff"
 *         />
 *                                        
 *
 * angle=270，从上到下 ：               
 *
 * xml代码：<gradient 
 *         android:startColor="#000000"
 *         android:endColor="#ffffff"
 *         android:angle="270"
 *         />
 * ---------------------
 * 作者：zjdyhant
 * 来源：CSDN
 * 原文：https://blog.csdn.net/zjdyhant/article/details/46537647
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 *
 */
public class LinearGradientView extends View{

    private Paint mPaint = null;

    private int mStartColor = Color.RED;
    private int mEndColor = Color.BLUE;

    public LinearGradientView(Context context) {
        super(context);

        mPaint = new Paint();
    }

    public LinearGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        LinearGradient linearGradient = new LinearGradient(0,0,0,100,new int[]{mStartColor, mEndColor}, null, Shader.TileMode.REPEAT);
        mPaint.setShader(linearGradient);
        canvas.drawRect(0, 0, 200, 200, mPaint);

        LinearGradient linearGradient1 = new LinearGradient(0,0,0,100,new int[]{mStartColor, mEndColor}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient1);
        canvas.drawRect(300, 0 , 500, 200, mPaint);


        LinearGradient linearGradient2 = new LinearGradient(0,0,0,100,new int[]{mStartColor, mEndColor}, null, Shader.TileMode.MIRROR);
        mPaint.setShader(linearGradient2);
        canvas.drawRect(600, 0 , 800, 200, mPaint);


        mPaint.setStyle( Paint.Style.FILL);
        LinearGradient linearGradient3 = new LinearGradient(0,0,0,100,new int[]{mStartColor, mEndColor}, null, Shader.TileMode.REPEAT);
        mPaint.setShader(linearGradient3);
        canvas.drawCircle(100f, 400f, 100f, mPaint);

        LinearGradient linearGradient4 = new LinearGradient(0,0,0,100,new int[]{mStartColor, mEndColor}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient4);
        canvas.drawCircle(300, 400f , 100f, mPaint);


        LinearGradient linearGradient5 = new LinearGradient(0,0,0,100,new int[]{mStartColor, mEndColor}, null, Shader.TileMode.MIRROR);
        mPaint.setShader(linearGradient5);
        canvas.drawCircle(500f, 400f , 100f, mPaint);
    }


    void draw1(Canvas canvas, Paint paint){
        //	<gradient android:type="linear" android:useLevel="true" android:startColor="#ff101010" android:endColor="#ff202020" android:angle="180" />
        LinearGradient linearGradient5 = new LinearGradient(0,0,0,100,new int[]{Color.parseColor("#ff101010"), Color.parseColor("#ff202020")}, null, Shader.TileMode.MIRROR);

        canvas.drawCircle(500f, 400f , 100f, mPaint);

    }
}
