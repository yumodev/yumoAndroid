package com.yumo.android.test.media.canvas.test

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

/**
 * Created by yumodev on 17/12/22.
 * 测试CircleView
 *
 * 绘制圆形
 * drawCircle(centerX, centerY, radius, paint)
 * centerX, centerY:圆心坐标
 * radius：圆的半径
 */

class TestCircleView(context: Context) : View(context) {
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint()
        //设置绘制颜色
        paint.color = Color.RED
        //设置填充类型
        paint.style = Paint.Style.STROKE
        //设置边框宽度
        paint.strokeWidth = 5f
        //画板背景。 定义白色画板背景
        canvas.drawColor(Color.WHITE)

        //昨上角绘制一个原型。
        canvas.drawCircle(100f, 100f, 100f, paint)

        paint.style = Paint.Style.FILL
        canvas.drawCircle(100f, 300f, 100f, paint)
    }
}
