package com.yumo.common.util;


import junit.framework.TestCase;

/**
 * 测试Reflect的使用
 */
public class ReflectTest extends TestCase {

    public void testInstance(){
        Class cls = null;
        try {
            cls = getClass().getClassLoader().loadClass("com.yumo.common.util.SingltonClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        SingltonClass singltonClass = Reflect.on(cls).call("getInstance").get();
        System.out.println((int)Reflect.on(singltonClass).call("getCount").get());
    }

    public void testInstance1(){
        SingltonClass singltonClass = Reflect.on("com.yumo.common.util.SingltonClass").call("getInstance").get();
        System.out.println((int)Reflect.on(singltonClass).call("getCount").get());
    }
}
