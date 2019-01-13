package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 9/12/16.
 */
public class Leetcode_0014_LongestCommonPrefix {
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0){
            return "";
        }


        for (int i = 0; i < strs[0].length(); i++){
            for (int j = 1; j < strs.length; j++){
                if (strs[j].length() <= i || strs[j].charAt(i) != strs[0].charAt(i)){
                    return strs[0].substring(0, i);
                }
            }
        }

        return strs[0];
    }

    public static void main(String[] args) {
        String[] str = {"121", "12", "21"};
        long startTime = System.nanoTime();
        String result = longestCommonPrefix(str);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("longestCommonPrefix:" + result + " time:" + time);
    }
}
