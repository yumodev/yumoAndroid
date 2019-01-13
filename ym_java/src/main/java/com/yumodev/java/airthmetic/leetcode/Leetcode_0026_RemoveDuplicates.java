package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 9/18/16.
 */
public class Leetcode_0026_RemoveDuplicates {

    public static int removeDuplicates(int[] nums){
        if (nums == null){
            return 0;
        }

        if (nums.length <= 1){
            return 1;
        }

        int i = 0, j = 1;
        while (i <nums.length && j < nums.length){
            if (nums[i] == nums[j]){
                j++;
            }else{
                nums[++i] = nums[j];
                j++;
            }
        }

        return i+1;
    }

    public static void main(String[] args){
        int[] nums = {2,7,11,15};
        long startTime = System.nanoTime();
        int result = removeDuplicates(nums);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println(" removeDuplicates:" + result + " time:" + time);
    }
}
