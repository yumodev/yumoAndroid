package com.yumo.android.test.kotlin.test

import com.yumo.common.log.Log

/**
 * Created by wks on 17/11/8.
 */
class TestFun {

    /**
     * 使用fun关键字定义函数,
     * 参数使用 name : type 命名方式
     * 方法返回值写在参数后面 :Int
     */
    fun add(a: Int, b: Int): Int{
        return a + b
    }


    /**
     * 方法体只有一行的时候、
     */
    fun add1(a: Int, b: Int) = a + b


    fun fun1(a:Int,b:Int = 0, c:Int = 0){
        Log.i(Log.LIB_TAG, "a:$a,b$b,c$c")
    }

    fun fun1(a:Int, b:Int = 0, c:Int = 0, d:Int){
        Log.i(Log.LIB_TAG,"a:$a,b$b,c$c,d$d")
    }
}