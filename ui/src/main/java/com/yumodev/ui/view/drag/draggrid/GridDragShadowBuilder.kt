package com.yumodev.ui.view.drag.draggrid

import android.graphics.Canvas
import android.graphics.Point
import android.view.View
import android.view.View.DragShadowBuilder

/**
 * Created by yumodev on 18/2/13.
 */
class GridDragShadowBuilder: DragShadowBuilder {

    constructor(view:View):super(view){

    }
    override fun onDrawShadow(canvas: Canvas?) {
        super.onDrawShadow(canvas)
    }

    override fun onProvideShadowMetrics(outShadowSize: Point?, outShadowTouchPoint: Point?) {
        //super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint)

        val view = getView()
        if (view != null) {
            outShadowSize?.set(view!!.getWidth(), view!!.getHeight())
            outShadowTouchPoint?.set(outShadowSize!!.x / 2, outShadowSize.y / 2)
        }
    }
}