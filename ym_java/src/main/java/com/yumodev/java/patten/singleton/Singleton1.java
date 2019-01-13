package com.yumodev.java.patten.singleton;

/**
 * Created by yumodev on 17/9/6.
 * 单例的懒汉模式
 */

public class Singleton1 {
    //自行生成一个类的实例。
    private static Singleton1 mInstance;

    //私有化构造方法。
    private Singleton1(){}

    //提供获取一个类的实例的接口
    public synchronized static Singleton1 getInstance(){
        if (mInstance == null){
            mInstance = new Singleton1();
        }
        return mInstance;
    }
}
