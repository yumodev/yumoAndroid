package com.yumo.android.test.media.custom;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class GradientProgressbar extends View {
    /*圆弧线宽*/
    private float circleBorderWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
    /*内边距*/
    private float circlePadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
    /*字体大小*/
    private float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 50, getResources().getDisplayMetrics());
    /*绘制圆周的画笔*/
    private Paint backCirclePaint;
    /*绘制圆周白色分割线的画笔*/
    private Paint linePaint;
    /*绘制文字的画笔*/
    private Paint textPaint;
    /*百分比*/
    private float percent = 62.5f;
    /*渐变圆周颜色数组*/
    //private int[] gradientColorArray = new int[]{Color.GREEN, Color.parseColor("#fe751a"), Color.parseColor("#13be23"), Color.GREEN};
    private int[] gradientColorArray = new int[]{Color.parseColor("#7F66E7"), Color.parseColor("#17C5E6")};
    private Paint gradientCirclePaint;

    public GradientProgressbar(Context context) {
        super(context);
        init();
    }

    public GradientProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradientProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        backCirclePaint = new Paint();
        backCirclePaint.setStyle(Paint.Style.STROKE);
        backCirclePaint.setAntiAlias(true);
        backCirclePaint.setColor(Color.LTGRAY);
        backCirclePaint.setStrokeWidth(circleBorderWidth);
//        backCirclePaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.OUTER));

        gradientCirclePaint = new Paint();
        gradientCirclePaint.setStyle(Paint.Style.STROKE);
        gradientCirclePaint.setAntiAlias(true);
        gradientCirclePaint.setColor(Color.LTGRAY);
        gradientCirclePaint.setStrokeWidth(circleBorderWidth);

        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(5);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(measureWidth, measureHeight), Math.min(measureWidth, measureHeight));
    }

    /**
     * drawArc 使用椭圆描述圆形的。left, top,right, bottom 描述的是弧形所在的椭圆。
     * startAngle 是弧形的起始角度（x 轴的正向，即正右的方向，是 0 度的位置；顺时针为正角度，逆时针为负角度
     * sweepAngle 是弧形划过的角度
     * useCenter 表示是否连接到圆心，如果不连接到圆心，就是弧形，如果连接到圆心，就是扇形
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.绘制灰色背景圆环
        canvas.drawArc(
                new RectF(circlePadding * 2, circlePadding * 2,
                        getMeasuredWidth() - circlePadding * 2, getMeasuredHeight() - circlePadding * 2), 90, 225, false, backCirclePaint);
        //2.绘制颜色渐变圆环
        LinearGradient linearGradient = new LinearGradient(circlePadding, circlePadding,
                getMeasuredWidth() - circlePadding,
                getMeasuredHeight() - circlePadding,
                gradientColorArray, null, Shader.TileMode.MIRROR);

        int centerX = getMeasuredWidth() / 2;
        SweepGradient sweepGradient = new SweepGradient(centerX, centerX, gradientColorArray, null);
        gradientCirclePaint.setShader(sweepGradient);
        //gradientCirclePaint.setShadowLayer(10, 10, 10, Color.RED);
        canvas.drawArc(
                new RectF(circlePadding * 2, circlePadding * 2,
                        getMeasuredWidth() - circlePadding * 2, getMeasuredHeight() - circlePadding * 2), 90, (float) (percent / 100.0) * 360, false, gradientCirclePaint);

        //半径
        float radius = (getMeasuredWidth() - circlePadding * 3) / 2;
        //X轴中点坐标
        centerX = getMeasuredWidth() / 2;

        //3.绘制100份线段，切分空心圆弧
        for (float i = 135; i < 360; i += 3.6) {
            double rad = i * Math.PI / 180;
            float startX = (float) (centerX + (radius - circleBorderWidth) * Math.sin(rad));
            float startY = (float) (centerX + (radius - circleBorderWidth) * Math.cos(rad));

            float stopX = (float) (centerX + radius * Math.sin(rad) + 1);
            float stopY = (float) (centerX + radius * Math.cos(rad) + 1);

            canvas.drawLine(startX, startY, stopX, stopY, linePaint);
        }
//
//        //4.绘制文字
//        float textWidth = textPaint.measureText(percent + "%");
//        int textHeight = (int) (Math.ceil(textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent) + 2);
//        canvas.drawText(percent + "%", centerX - textWidth / 2, centerX + textHeight / 4, textPaint);
    }

    /**
     * 设置百分比
     *
     * @param percent
     */
    public void setPercent(int percent) {
        if (percent < 0) {
            percent = 0;
        } else if (percent > 100) {
            percent = 100;
        }

        this.percent = percent;
        invalidate();
    }
}
