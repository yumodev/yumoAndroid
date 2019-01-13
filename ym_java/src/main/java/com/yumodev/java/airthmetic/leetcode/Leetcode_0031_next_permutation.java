package com.yumodev.java.airthmetic.leetcode;

import java.util.Arrays;

/**
 * Created by yumodev on 17/4/1.
 */

public class Leetcode_0031_next_permutation {

    public static void nextPermutation(int[] nums) {
        if (nums == null || nums.length < 2){
            return;
        }

        int i = nums.length - 2;
        while (i >= 0 && nums[i + 1] < nums[i]){
            i --;
        }

        if (i < 0){
            Arrays.sort(nums);
            return;
        }

        int temp = nums[i];
        while (i + 1 < nums.length -1 && nums[i] < nums[i+1]){

        }

    }

    public static void test(int[] nums){
        long startTime = System.nanoTime();
        nextPermutation(nums);
        long endTime = System.nanoTime();
        System.out.println(String.format("time :" + (endTime - startTime)+" result: %s", Arrays.toString(nums)));
    }


    public static void main(String[] args){
        test(new int[]{1,3,2});//2,1,3
        test(new int[]{1,2,3});//1,3,2
        test(new int[]{1,1,5});//1,5,1
        test(new int[]{3,2,1});//1,2,3
        test(new int[]{6,5,4,8,7,5,1});
    }
}
