package com.yumodev.java.patten.singleton;

/**
 * Created by yumodev on 17/9/6.
 */

public class SingletonEhan {
    //自行生成一个类的实例。
    private static SingletonEhan mInstance = new SingletonEhan();

    //私有化构造方法。
    private SingletonEhan(){}

    //提供获取一个类的实例的接口
    public static SingletonEhan getInstance(){
        return mInstance;
    }

    public static void main(String[] args){
        SingletonEhan obj1 = SingletonEhan.getInstance();
        SingletonEhan obj2 = SingletonEhan.getInstance();
        if (obj1 == obj2){
            System.out.println("obj1 和 obj2 对象相同");
        }else{
            System.out.println("obj1 和 obj2 对象不同");
        }
    }
}
