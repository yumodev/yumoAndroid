package com.yumodev.java.airthmetic.leetcode;


/**
 * Created by yumodev on 9/19/16.
 */
public class Leetcode_0028_StrStr {

    /**
     * 暴力法
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr(String haystack, String needle){
        if (haystack == null || needle == null || needle.length() == 0){
            return 0;
        }

        for (int i = 0; i < (haystack.length() - needle.length()); i++){
            if (haystack.charAt(i) != needle.charAt(0)) {
                continue;
            }

            int j = 1;
            for (;j < needle.length(); j++){
                if (needle.charAt(j) != haystack.charAt(i+j)){
                    break;
                }
            }

            if (needle.length() == j){
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args){
        String haystack = "aaa";
        String needle = "aaaa";

        long startTime = System.nanoTime();
        int result = strStr(haystack, needle);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println(" strStr:" + result + " time:" + time);
    }
}
