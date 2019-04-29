package com.yumodev.kotlin.test.lambda

import android.view.View
import junit.framework.TestCase

/**
 * Created by yumodev on 18/2/12.
 */

class LambdaTest:TestCase(){


    fun testFunction(){
        val print = fun(i:Int) {
            println(i)
        }

        (1..4).forEach(print)
    }


    fun testFunctionReturn(){

        (1..4).forEach testReturn@{
            if (it == 2) return@testReturn
            println(it)
        }
    }

    /**
     * lambda表达式 使用{}包裹。
     */
    fun testAdd(){
        val add = {a: Int, b: Int -> a + b}
        println(add(1,2))
    }


    /**
     * lambda表达式用作参数
     */
    fun testAdd1(){
        fun foo(a: String, b: (String) -> String) {
            println(b(a))
        }
        foo("xxx", { s -> s + "xxx" })
    }


    /**
     * 当lambda只接受一个参数的时候，该参数可以省略，使用时用 it 来标识
     */
    fun testAdd2(){
        fun foo(a: String, b: (String) -> String) {
            println(b(a))
        }
        foo("xxx", { it + "xxx" })
    }


    /**
     * 当最后一个参数为函数时，该函数可以写在()外，并用{}包裹
     */
    fun testAdd3(){
        fun foo(a: String, b: (String) -> String) {
            println(b(a))
        }
        foo("xxx"){ it + "xxx" }
    }



    /**
     * 当最后一个参数为函数时，该函数可以写在()外，并用{}包裹
     */
    fun testAdd4(){
        fun foo(b: (String) -> Unit) {
            println(b("hello"))
        }
        foo{ it + "xxx" }
    }


    /**
     */
    fun testAdd5(){
        fun add1(a:Int, b:Int, sum: (Int, Int) -> Int) {
            println(sum(a, b))
        }
        add1(1,2, {a, b -> a + b})
    }

    /**
     * 当参数的函数体中没有引用时，可以将其设置为_,若此时只有一个参数
     */
    fun testAdd6(){
        fun add1(a:Int, b:Int, sum: (Int, Int) -> Int) {
            println(sum(a, b))
        }
        add1(1,2, {_, b -> b})
    }


    interface onDragListener{
        fun onDragEvent(a:Int, b:Int) : Boolean
    }

    class TestView{
        fun addOnDragLister(listener:onDragListener){

        }
    }

    fun testOnDragListener(){
        var testView = TestView()
        testView.addOnDragLister(object : onDragListener{
            override fun onDragEvent(a: Int, b: Int): Boolean {
               return true
            }
        })
    }



}
