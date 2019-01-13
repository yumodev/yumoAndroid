package com.yumodev.java.thread;

import junit.framework.TestCase;

/**
 * Created by wks on 18/1/10.
 */

public class ThreadInfo  {

    public static void testGetState(){
        System.out.println(Thread.currentThread().getName()+" " +Thread.currentThread().getState());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getState());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getState());
            }
        });
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());

    }

    public static void main(String[] args) {
       testGetState();
    }
}
