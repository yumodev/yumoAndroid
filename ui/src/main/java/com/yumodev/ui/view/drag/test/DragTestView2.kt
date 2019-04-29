package com.yumodev.ui.view.drag.test

import android.content.ClipData
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.DragEvent
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.yumo.common.android.YmDisplayUtil

import com.yumodev.ui.recyclerview.Define

/**
 * Created by yumodev on 17/12/21.
 * 一个九宫格移动位置
 */

class DragTestView2(context: Context) : LinearLayout(context) {

    private var btnWidth = 0
    private val btnSpace = 100
    private lateinit var dragView : Button
    init {
        initView()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (changed) {
            var left = btnSpace
            var top = btnSpace
            for (i in 0 until childCount) {
                if ( i % 3 == 0){
                   left = btnSpace
                }else{
                    left += btnWidth + btnSpace
                }

                top = (i/3 + 1) * btnSpace + (i/3)*btnWidth

                val view = getChildAt(i)
                view.layout(left, top , left + btnWidth, top + btnWidth)

            }
        }
    }

    private fun initView() {
        orientation = LinearLayout.HORIZONTAL

        val width = context.resources.displayMetrics.widthPixels;
        btnWidth = ( width - 4 * btnSpace ) / 3
        for (i in 0..8) {
            val btn = Button(context)
            btn.text = i.toString()
            btn.gravity = Gravity.CENTER
            btn.width = btnWidth
            btn.height = btnWidth
            addView(btn)

            btn.setOnLongClickListener { v ->
                val data = ClipData.newPlainText("value", getText(v))
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    v.startDrag(data, View.DragShadowBuilder(v), null, 0)
                } else {
                    v.startDragAndDrop(data, View.DragShadowBuilder(v), null, 0)
                }
                dragView = v as Button
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

                        val dragVal = getText(dragView)

                        //得到textview的值
                        val dropVal = getText(v)

                        dragView.text = dropVal
                        if(v is Button) v.text = dragVal
                        return@OnDragListener true
                    }

                    DragEvent.ACTION_DRAG_ENDED -> {
                        v.setBackgroundColor(Color.WHITE)
                        Log.i(Define.LOG_TAG, "$value action_drag_ended")
                        if (v == dragView){
                            Toast.makeText(context, "拖拽完成", Toast.LENGTH_SHORT).show()
                        }
                        return@OnDragListener true
                    }
                }

                false
            })
        }
    }

    fun getText(v : View):String{
        if(v is Button){
            return v.text.toString();
        }
        return ""
    }
}
