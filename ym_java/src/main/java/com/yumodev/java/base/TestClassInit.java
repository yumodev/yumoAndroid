package com.yumodev.java.base;

/**
 * Created by yumodev on 10/1/16.
 * 测试类的初始化
 */
public class TestClassInit {

    public TestClassInit() {
        System.out.println("TestClassInit()");
    }

    public TestClassInit(int n) {
        System.out.println("TestClassInit(n) " + n);
    }

    {
        System.out.println("TestClassInit code");
    }

    public static StaticInnerClass mSic1 = new StaticInnerClass();

    static {
        System.out.println("static");
    }


    public class InnerClass {
        public InnerClass() {
            System.out.println("innerClass");
        }
    }

    public static class StaticInnerClass {

        public StaticInnerClass() {
            System.out.println("StaticInnerClass()");
        }

        static {
            System.out.println("StaticInnerClass");
        }
    }

    public static void main(String[] args) {
        TestClassInit testClassInit = new TestClassInit();
        TestClassInit.InnerClass innerClass = testClassInit.new InnerClass();
        //StaticInnerClass staticInnerClass = new StaticInnerClass();
    }
}
