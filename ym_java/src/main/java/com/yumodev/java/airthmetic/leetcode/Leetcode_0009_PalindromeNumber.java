package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 8/25/16.
 */
public class Leetcode_0009_PalindromeNumber {
    public static  boolean isPalindrome(int x) {
        if (x < 0){
            return false;
        }

        int los = 0;
        int temp = x;

        while (temp > 0){
            los = los * 10 + temp % 10;
            temp = temp / 10;
        }

        return los == x;
    }

    public static void main(String[] args) {
        int number = 121;
        long startTime = System.nanoTime();
        boolean result = isPalindrome(number);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("reverse:" + result + " time:" + time);
    }
}
