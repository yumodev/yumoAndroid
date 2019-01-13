package com.yumodev.java.test;

/**
 * Created by yumo on 7/31/16.
 * 实现一个基本的字符串压缩功能
 * 利用字符串出现的次数,实现基本的字符串压缩功能.比如字符串aabcccccaaa 会变a2b1c5a3
 * 如果压缩后字符长长度不变,就返回原字符串.
 */
public class CompressString {

    public static String compressStr(String str){
        for (int i =0; i<str.length(); i++);
        return str;
    }


    public static void main(String[] args) {
        System.out.println("testList");
        String str = "aabcccccaaa";

        long startTime = System.nanoTime();
        String str1 = compressStr(str);
        long endTime = System.nanoTime();
        System.out.println("isStringEqualByArraysSort: " + str1+ " time:" + (endTime - startTime));
    }
}
