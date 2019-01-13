package com.yumo.android.test.kotlin

import com.yumo.android.test.kotlin.test.TestFun
import com.yumo.android.test.kotlin.test.TestStatic
import com.yumo.demo.view.YmTestFragment

/**
 * Created by yumodev on 17/11/6.
 */
class KotlinTestView : YmTestFragment() {
    public fun testShowTest(){
        showToastMessage("TestToast")
    }

    public fun testShowTest1(){
        showToastMessage("TestToast")
    }

    public fun testShowTest2(){
        showToastMessage("TestToast")
    }

    public fun testStatic(){
        TestStatic.staticFun()
        showToastMessage(TestStatic.valStatic.toString())
    }

    public fun testFun(){
        val testFun = TestFun();
        testFun.fun1(1);
        testFun.fun1(1,2,3)
        testFun.fun1(1, d=4)
    }
}