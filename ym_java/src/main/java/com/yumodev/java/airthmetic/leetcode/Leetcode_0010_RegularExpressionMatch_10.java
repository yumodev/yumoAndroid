package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 8/26/16.
 */
public class Leetcode_0010_RegularExpressionMatch_10 {
    public static boolean isMatch(String s, String p) {
        if (s == null || s.length() ==0){
            return false;
        }

        if (s.indexOf(p) >=0){
            return true;
        }

        if (p.equals(".*")){
            return true;
        }

        boolean[] match = new boolean[s.length() + 1];
        match[s.length()] = true;
        for(int i = p.length() - 1; i >=0; i--){
            if(p.charAt(i) == '*'){
                // 如果是星号，从后往前匹配
                for(int j = s.length() - 1; j >= 0; j--){
                    match[j] = match[j] || (match[j + 1] && (p.charAt(i - 1) == '.' || (p.charAt(i - 1) == s.charAt(j))));
                }
                // 记得把i多减一，因为星号是和其前面的字符配合使用的
                i--;
            } else {
                // 如果不是星号，从前往后匹配
                for(int j = 0; j < s.length(); j++){
                    match[j] = match[j + 1] && (p.charAt(i) == '.' || (p.charAt(i) == s.charAt(j)));
                }
                // 只要试过了pattern中最后一个字符，就要把match[s.length()]置为false
                match[s.length()] = false;
            }
        }
        return match[0];

    }

    public static void main(String[] args) {
        String str = "d";
        String p = "c*a*b";

        long startTime = System.nanoTime();
        boolean result = isMatch(str, p);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("reverse:" + result + " time:" + time);
    }
}
