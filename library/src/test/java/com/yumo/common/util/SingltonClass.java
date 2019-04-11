package com.yumo.common.util;

public class SingltonClass {

    private SingltonClass(){}

    public static SingltonClass getInstance(){
        return SingltonHolder.instance;
    }

    public int getCount(){
        return 10;
    }

    private static class SingltonHolder{
        private static final SingltonClass instance = new SingltonClass();
    }
}
