package com.yumodev.java.test;

import java.util.Stack;

/**
 * Created by yumo on 7/31/16.
 * 字符串翻转\字符数组
 */
public class ReverseString {

    /**
     * 利用系统自带的StringBuilder.reverse() 进行反转
     * @param str
     * @return
     */
    public static String reverseByStringBuilder(String str){
        if (str == null){
            return "";
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * 使用字符串进行二分反转
     * @return
     */
    public static String reverseByCharArray(String str){
        if (str == null){
            return "";
        }

        char[] chArr = str.toCharArray();
        int len = chArr.length;
        for (int i = (len-1) / 2; i >= 0; i--){
            char c = chArr[i];
            chArr[i] = chArr[len - i -1];
            chArr[len - i -1] = c;
        }

        return new String(chArr);
    }

    /**
     * 使用异或进行翻转
     * 将二进制 a = 1101, b = 1000 进行反转.
     * 1  a = 1101 ^ 1000 此时 a = 1010, b = 1000
     * 2  b = 1000 ^ 1010 此时 a = 1010, b = 1101
     * 3  a = 1101 ^ 1010 此时 a = 1000, b = 1101
     * @return
     */
    public static String reverseByXor(String str){
        if (str == null){
            return "";
        }

        char[] chArr = str.toCharArray();
        int len = chArr.length ;
        for (int i = 0, j = len -1; i < len/2; i++, j--){
            chArr[i] = (char)(chArr[i]^chArr[j]);
            chArr[j] = (char)(chArr[j]^chArr[i]);
            chArr[i] = (char)(chArr[j]^chArr[i]);
        }

        return new String(chArr);
    }

    /**
     * 使用栈进行翻转
     * @param str
     * @return
     */
    public static String reverseByStack(String str){
        if (str == null){
            return "";
        }

        Stack<Character> stack = new Stack<>();
        int len = str.length();
        for (int i = 0; i < len; i++){
            stack.push(str.charAt(i));
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++){
            sb.append(stack.pop());
        }
        return sb.toString();
    }



    public static void main(String[] args){
        System.out.println("testList");
        String str="";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<= 10000; i++){
            sb.append(i+"");
        }
        str = sb.toString();


        long startTime = System.nanoTime();
        String str1 = reverseByStringBuilder(str);
        long endTime = System.nanoTime();
        System.out.println("StringBuilder.reverse() time:"+(endTime - startTime)+"  "+str1);


        startTime = System.nanoTime();
        str1 = reverseByCharArray(str);
        endTime = System.nanoTime();
        System.out.println("reverseByCharArray time:"+(endTime - startTime)+" "+str1);

        startTime = System.nanoTime();
        str1 = reverseByXor(str);
        endTime = System.nanoTime();
        System.out.println("reverseByXor time:"+(endTime - startTime)+" "+str1);

        startTime = System.nanoTime();
        str1 = reverseByStack(str);
        endTime = System.nanoTime();
        System.out.println("reverseBystack time:"+(endTime - startTime)+" "+str1);
    }
}
