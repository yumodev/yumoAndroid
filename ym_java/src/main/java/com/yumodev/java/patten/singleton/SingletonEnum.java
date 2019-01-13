package com.yumodev.java.patten.singleton;

/**
 * Created by yumodev on 17/9/6.
 */

public enum SingletonEnum {
    INSTANCE;

    public void doSomething(){
        System.out.println("doSomething");
    }

    public static void main(String[] args){
        SingletonEnum obj1 = SingletonEnum.INSTANCE;
        SingletonEnum obj2 = SingletonEnum.INSTANCE;
        if (obj1 == obj2){
            SingletonEnum.INSTANCE.doSomething();
            System.out.println("obj1 和 obj2 对象相同:");
        }else{
            System.out.println("obj1 和 obj2 对象不同");
        }
    }
}
