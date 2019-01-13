package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by wks on 8/8/16.
 */
public class Leetcode_0005_LongestPalindrome {
    /**
     * 利用动态规划的方法.
     * @param s
     * @return
     */
    public static String longestPalindrome(String s){
        if (s == null || s.length() == 1) {
            return s;
        }

        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        int begin = 0, end = 0, max = 1;

        for (int i = len - 1; i >= 0; i--){
            for (int j = i; j < len; j++){
                if (s.charAt(i) == s.charAt(j)){
                    if (j - i <= 2 ||  (dp[i+1][j-1] && j-1 > 0)){
                        dp[i][j] = true;
                        if (max < j - i + 1){
                            max = j - i + 1;
                            begin = i;
                            end = j;
                        }
                    }
                }
            }
        }


        return s.substring(begin, end+1);
    }

    public static void main(String[] args) {
        String str = "1aba12";
        long startTime = System.nanoTime();
        String palindrome = longestPalindrome(str);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("palindrome:" + palindrome + " time:" + time);
    }
}
