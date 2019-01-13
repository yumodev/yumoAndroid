package com.yumodev.java.patten.singleton;

/**
 * Created by yumodev on 17/9/6.
 */

public class Singleton {
    //自行生成一个类的实例。
    private volatile static Singleton mInstance;

    //私有化构造方法。
    private Singleton(){}

    //提供获取一个类的实例的接口
    public synchronized static Singleton getInstance(){
        if (mInstance == null){
            mInstance = new Singleton();
        }
        return mInstance;
    }

    public static void main(String[] args){
        Singleton obj1 = Singleton.getInstance();
        Singleton obj2 = Singleton.getInstance();
        if (obj1 == obj2){
            System.out.println("obj1 和 obj2 对象相同");
        }else{
            System.out.println("obj1 和 obj2 对象不同");
        }
    }
}
