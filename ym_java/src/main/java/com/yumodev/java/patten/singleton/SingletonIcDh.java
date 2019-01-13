package com.yumodev.java.patten.singleton;

/**
 * Created by yumodev on 17/9/6.
 */

public class SingletonIcDh {
    //私有化构造方法。
    private SingletonIcDh(){}

    //提供获取一个类的实例的接口
    public synchronized static SingletonIcDh getInstance(){
        return SingletonHolder.mInstance;
    }

    //静态内部类
    private static class SingletonHolder{
        //创建单例对象
        private static final SingletonIcDh mInstance = new SingletonIcDh();
    }

    public static void main(String[] args){
        SingletonIcDh obj1 = SingletonIcDh.getInstance();
        SingletonIcDh obj2 = SingletonIcDh.getInstance();
        if (obj1 == obj2){
            System.out.println("obj1 和 obj2 对象相同");
        }else{
            System.out.println("obj1 和 obj2 对象不同");
        }
    }
}
