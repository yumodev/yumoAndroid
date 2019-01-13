package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 9/18/16.
 */
public class Leetcode_0027_RemoveElement {

    public static int removeElement(int[] nums, int val){
        if (nums == null || nums.length == 0){
            return 0;
        }


        int i = 0;
        for (int j = 0; j < nums.length; j++){
            if (nums[j] != val){
                nums[i] = nums[j];
                i++;
            }
        }

        return i;
    }

    public static void main(String[] args){
        int[] nums = {3,2,2,3};
        int value = 3;
        long startTime = System.nanoTime();
        int result = removeElement(nums, value);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println(" removeElement:" + result + " time:" + time);
    }
}
