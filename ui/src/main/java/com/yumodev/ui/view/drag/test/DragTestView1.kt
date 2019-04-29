package com.yumodev.ui.view.drag.test

import android.content.ClipData
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

import com.yumodev.ui.recyclerview.Define

/**
 * Created by yumodev on 17/12/21.
 * 一个九宫格移动位置
 */

class DragTestView1(context: Context) : LinearLayout(context) {

    init {
        initView()
    }

    val subWidth = 300

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.i(Define.LOG_TAG, "onMeasure")
        for (i in 0 until childCount){
            var view = getChildAt(i)
            view.measure(subWidth, subWidth)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (changed) {
            var left = 0
            var top = 50
            for (i in 0 until childCount) {
                val view = getChildAt(i)
                Log.i(Define.LOG_TAG, view.measuredWidth.toString()+" "+view.measuredHeight)
                if (i % 3 == 0 && i / 3 > 0){
                    left = 0
                    top += view.measuredHeight + 50
                }
                view.layout(left, top, left + view.measuredWidth, top + view.measuredHeight)
                left += view.measuredWidth + 100
            }
            invalidate()
        }

        Log.i(Define.LOG_TAG, "onLayout")
    }

    private fun initView() {
        orientation = LinearLayout.HORIZONTAL
        for (i in 0..8) {
            val btn = Button(context)
            btn.text = i.toString()
            addView(btn)

            btn.setOnLongClickListener { v ->
                val data = ClipData.newPlainText("value", getText(v))
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    v.startDrag(data, View.DragShadowBuilder(v), null, 0)
                } else {
                    v.startDragAndDrop(data, View.DragShadowBuilder(v), null, 0)
                }
                false
            }

            btn.setOnDragListener(OnDragListener { v, event ->
                val value = getText(v)
                when (event.action) {
                    DragEvent.ACTION_DRAG_STARTED -> {
                        Log.i(Define.LOG_TAG,  "$value action_drag_started")
                        return@OnDragListener true
                    }

                    DragEvent.ACTION_DRAG_ENTERED -> {
                        v.setBackgroundColor(Color.YELLOW)
                        Log.i(Define.LOG_TAG, "$value action_drag_entered")
                        return@OnDragListener true
                    }

                    DragEvent.ACTION_DRAG_LOCATION -> {
                        Log.i(Define.LOG_TAG, "$value action_drag_location")
                        return@OnDragListener true
                    }

                    DragEvent.ACTION_DRAG_EXITED -> {
                        v.setBackgroundColor(Color.RED)
                        Log.i(Define.LOG_TAG, "$value action_drag_exited")
                        return@OnDragListener true
                    }

                    DragEvent.ACTION_DROP -> {
                        Log.i(Define.LOG_TAG, "$value action_drop")
                        v.setBackgroundColor(Color.GREEN)
                        //得到拖动的值
                        val dragVal = Integer.parseInt(event.clipData.getItemAt(0).text.toString())
                        //得到textview的值
                        val viewVal = Integer.parseInt(value)
                        if(v is Button) v.text = "" + (dragVal + viewVal)
                        return@OnDragListener true
                    }

                    DragEvent.ACTION_DRAG_ENDED -> {
                        v.setBackgroundColor(Color.WHITE)
                        Log.i(Define.LOG_TAG, "$value action_drag_ended")
                        return@OnDragListener true
                    }
                }

                false
            })
        }
    }

    private fun getText(v : View):String{
        if(v is Button){
            return v.text.toString();
        }
        return ""
    }
}
