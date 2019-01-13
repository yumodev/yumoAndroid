package com.yumo.android.test.kotlin.test

import com.yumo.common.log.Log

/**
 * Created by yumodev on 17/11/6.
 */
object TestStatic {

    fun staticFun(){
        Log.i(Log.LIB_TAG,"静态方法：staticFun")
    }

    val valStatic = 0
}