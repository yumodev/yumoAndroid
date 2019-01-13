package com.yumodev.java.jni;

public class TestJni {

    static {
        System.loadLibrary("testjni");
    }

    public native void helloword();
}
