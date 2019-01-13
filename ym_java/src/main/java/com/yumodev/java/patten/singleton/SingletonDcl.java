package com.yumodev.java.patten.singleton;

/**
 * Created by guo on 17/9/6.
 */

public class SingletonDcl {
    //自行生成一个类的实例。
    private volatile static SingletonDcl mInstance;

    //私有化构造方法。
    private SingletonDcl(){}

    //提供获取一个类的实例的接口
    public static SingletonDcl getInstance(){
        if (mInstance == null){
            synchronized (SingletonDcl.class){
                mInstance = new SingletonDcl();
            }
        }
        return mInstance;
    }
}
