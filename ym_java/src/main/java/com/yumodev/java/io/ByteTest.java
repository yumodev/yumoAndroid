package com.yumodev.java.io;

import junit.framework.TestCase;

/**
 * Created by yumo on 2018/4/26.
 */

public class ByteTest extends TestCase {

    public void testByteArray(){
        String hello = "Hello";
        byte[] bytes = hello.getBytes();
        for (byte b : bytes){
            System.out.println(b);
        }
    }

    public void testByteArray1(){
        String hello = "禹墨Hello";
        byte[] bytes = hello.getBytes();
        System.out.print("{");
        for (byte b : bytes){
            System.out.print(b+",");
        }
        System.out.print("}");
    }


    public void testByteToArray(){
        byte[] b={72,101,108,108, 111};
        System.out.print(new String(b));
    }

    public void testByteToArray1(){
        byte[] b={-25,-90,-71,-27,-94,-88,72,101,108,108,111};
        System.out.print(new String(b));
    }
}
