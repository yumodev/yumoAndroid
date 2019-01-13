package com.yumo.android.test.kotlin;

import com.yumo.common.test.TestReflect;
import com.yumo.demo.view.YmTestFragment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yumodev on 17/11/7.
 */

public class JavaKotlinTestView extends YmTestFragment {

    public void testReflectObject(){
        try {
            Class cls = getContext().getClassLoader().loadClass("com.yumo.android.test.kotlin.test.TestStatic");
            Field instance = cls.getField("INSTANCE");
            Method method = cls.getMethod("staticFun", new Class[0]);
            method.invoke(instance.get(null), new Object[0]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void testPrintClass(){
        TestReflect.printClassInfo("com.yumo.android.test.kotlin.test.TestStatic");
    }

    public void testReflectObject1(){
        try {
            Class cls = getContext().getClassLoader().loadClass("com.yumo.android.test.kotlin.test.TestStatic1");
            Field companion = cls.getField("Companion");
            Object companionObject = companion.get(null);
            TestReflect.printClassInfo(companionObject.getClass());
            Method method = companionObject.getClass().getMethod("staticFun", new Class[0]);
            method.invoke(companionObject, new Object[0]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void testPrintClass1(){
        TestReflect.printClassInfo("com.yumo.android.test.kotlin.test.TestStatic1");
    }
}
