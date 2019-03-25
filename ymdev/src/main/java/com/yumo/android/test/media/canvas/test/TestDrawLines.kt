package com.yumo.android.test.media.canvas.test

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View


/**
 * Created by yumodev on 17/12/22.
 * 画线
 * drawLines(float[] psts, int offset, int count, Paint paint)
 *
 * drawLines(float[] pts, Paint paint)
 */

class TestDrawLines(context : Context) : View(context){
    init {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint()
        //设置绘制颜色
        paint.color = Color.RED
        //设置填充类型
        paint.style = Paint.Style.STROKE
        //设置边框宽度
        paint.strokeWidth = 10f
        //画板背景。 定义白色画板背景
        canvas?.drawColor(Color.WHITE)

        val width = measuredWidth
        val height = measuredHeight

        val  pts : FloatArray = floatArrayOf(0f, 0f, width.toFloat(), height.toFloat())
        canvas?.drawLines(pts, paint)
    }
}
