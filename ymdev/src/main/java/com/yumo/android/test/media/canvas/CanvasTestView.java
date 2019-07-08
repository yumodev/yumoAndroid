package com.yumo.android.test.media.canvas;

import android.view.View;

import com.yumo.android.test.media.canvas.test.TestArc1View;
import com.yumo.android.test.media.canvas.test.TestLinearGradient1;
import com.yumo.demo.view.YmTestFragment;
import com.yumo.android.test.media.canvas.test.CanvasDemoView;
import com.yumo.android.test.media.canvas.test.LinearGradientView;
import com.yumo.android.test.media.canvas.test.RadialGradientView;
import com.yumo.android.test.media.canvas.test.SweepGradientView;
import com.yumo.android.test.media.canvas.test.TestArcView;
import com.yumo.android.test.media.canvas.test.TestBitmapDrawView;
import com.yumo.android.test.media.canvas.test.TestCircleView;
import com.yumo.android.test.media.canvas.test.TestDrawLines;
import com.yumo.android.test.media.canvas.test.TestDrawPointView;
import com.yumo.android.test.media.canvas.test.TestDrawRectView;
import com.yumo.android.test.media.canvas.test.TestDrawText;
import com.yumo.android.test.media.canvas.test.TestOvalView;
import com.yumo.android.test.media.canvas.test.TestPathView;

/**
 * Created by yumodev on 17/9/21.
 *
 * [HenCoder Android 开发进阶: 自定义 View 1-1 绘制基础](http://hencoder.com/ui-1-1/)
 * Canvas
 *
 *
 * Paint
 *
 * setColor：设置绘制的颜色
 * setStyle(): FILL：填充模式, STROKE：实线，FILL_AND_STROKE：
 * setStrokeWidth:设置线条的宽度
 * setAntiAlias:设置抗锯齿，设置了抗锯齿后更加光滑
 */

public class CanvasTestView extends YmTestFragment {

    public void testShowCanvasDemo(){
        View view = new CanvasDemoView(getContext());
        showTestView(view);
    }

    /**
     * 练习绘制圆形
     */
    public void testCircleView(){
        showTestView(new TestCircleView(getContext()));
    }


    /**
     * 练习绘制椭圆
     */
    public void testOvalView(){
        showTestView(new TestOvalView(getContext()));
    }


    /**
     * 绘制圆形和扇形
     */
    public void testArcView(){
        showTestView(new TestArcView(getContext()));
    }

    /**
     * 绘制圆形和扇形
     */
    public void testArc1View(){
        showTestView(new TestArc1View(getContext()));
    }


    /**
     * 测试绘制矩形
     */
    public void testTestDrawRectView(){
        showTestView(new TestDrawRectView(getContext()));
    }

    /**
     * 绘制点
     */
    public void testTestPointView(){
        showTestView(new TestDrawPointView(getContext()));
    }

    /**
     * 绘制线
     */
    public void testLinesView() {
        showTestView(new TestDrawLines(getContext()));
    }


    public void testPathView(){
        showTestView(new TestPathView(getContext()));
    }

    public void testDrawBitmap(){
        showTestView(new TestBitmapDrawView(getContext()));
    }

    public void testDrawText(){
        showTestView(new TestDrawText(getContext()));
    }

    /**
     * 渐变色
     */
    public void testLinearGradientView() {
        showTestView(new LinearGradientView(getContext()));
    }

    /**
     * 渐变色
     */
    public void testLinearGradientView1() {
        showTestView(new TestLinearGradient1(getContext()));
    }

    /**
     * 放射性渐变
     */
    public void testRadialGradientView() {
        showTestView(new RadialGradientView(getContext()));
    }

    /**
     * 扫描渐变
     */
    public void testSweepGradientView(){
        showTestView(new SweepGradientView(getContext()));
    }

}
