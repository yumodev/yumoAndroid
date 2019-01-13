package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 8/17/16.
 * 反正整形字符串
 */
public class Leetcode_0007_ReverseInteger_7 {
    public static int reverse(int x) {
        long result = 0;
        while ( x != 0){
            result = result * 10 + x % 10;
            x = x / 10;

            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE){
                result = 0;
                break;
            }
        }

        return (int)result;
    }

    public static void main(String[] args) {
        int x  = 1000000003;
        long startTime = System.nanoTime();
        int result = reverse(x);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("reverse:" + result + " time:" + time);
    }
}
