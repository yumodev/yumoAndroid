package com.yumodev.java.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yumo on 7/31/16.
 * 比较两个字符排序后是否相等
 */
public class StringIsSameAfterSort {


    private static String sort(String str){
        char[] chArr = str.toCharArray();
        Arrays.sort(chArr);
        return new String(chArr);
    }

    /**
     * 通过Arrays.sort进行比较
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isStringEqualByArraysSort(String str1, String str2){
        if (str1 == null || str2 == null){
            return false;
        }

        if (str1.length() != str2.length()){
            return false;
        }

        return sort(str1).equals(sort(str2));
    }

    private static Map convertMapNums(String str){
        Map<Character, Integer> map= new HashMap<>();
        for (int i = 0; i <  str.length(); i++){
            char c = str.charAt(i);
            if (map.get(c) == null){
                map.put(c,  1);
            }else{
                map.put(c,  map.get(c)+1);
            }
        }

        return map;
    }

    /**
     * 将两个字符串的字符和数量放入Map中.然后比较Map中每个字符数量是否相等
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isStringEqualByCharNums(String str1, String str2){
        if (str1 == null || str2 == null){
            return false;
        }

        if (str1.length() != str2.length()){
            return false;
        }

        if (str1.length() == str2.length() && str1.length() == 0){
            return true;
        }

        Map<Character, Integer> map1 = convertMapNums(str1);
        Map<Character, Integer> map2 = convertMapNums(str2);

        if (map1.size() != map2.size()){
            return false;
        }

        boolean isSame = true;
        Set<Character> set1 = map1.keySet();
        for (Character ch: set1) {
            if (map1.get(ch) != map2.get(ch)){
                isSame = false;
                break;
            }
        }

        return isSame;
    }


    /**
     * 如果是Ascii编码,那么不同字符的数量最大为256个,所以可以声明一个长度256的数组,将字符的数量放到这个数组中,然后在一一比较
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isStringEqualByCharNumsAscii(String str1, String str2){
        if (str1 == null || str2 == null){
            return false;
        }

        if (str1.length() != str2.length()){
            return false;
        }

        if (str1.length() == str2.length() && str1.length() == 0){
            return true;
        }

        int[] chArr1 = new int[256];
        for (int i = 0; i < str1.length(); i++){
            char ch = str1.charAt(i);
            chArr1[ch] = chArr1[ch]+1;
        }

        int[] chArr2 = new int[256];
        for (int i = 0; i <str2.length(); i++){
            char ch = str2.charAt(i);
            chArr2[ch] = chArr2[ch]+1;
        }

        boolean isSame = true;
        for (int i = 0; i < 256; i++){
            if (chArr1[i] != chArr2[i]){
                isSame = false;
                break;
            }
        }

        return isSame;
    }

    public static void main(String[] args){
        System.out.println("testList");
        String str1="1234567333";
        String str2="7654321333";

        long startTime = System.nanoTime();
        boolean isSame = isStringEqualByArraysSort(str1, str2);
        long endTime = System.nanoTime();
        System.out.println("isStringEqualByArraysSort: "+isSame+" time:"+(endTime - startTime));

        startTime = System.nanoTime();
        isSame = isStringEqualByCharNums(str1, str2);
        endTime = System.nanoTime();
        System.out.println("isStringEqualByCharNums: "+isSame+" time:"+(endTime - startTime));

        startTime = System.nanoTime();
        isSame = isStringEqualByCharNumsAscii(str1, str2);
        endTime = System.nanoTime();
        System.out.println("isStringEqualByCharNumsAscii: "+isSame+" time:"+(endTime - startTime));
    }
}
