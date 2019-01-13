package com.yumodev.java.airthmetic.sort;

import java.util.Arrays;

/**
 * Created by yumodev on 18/2/10.
 * 希尔排序
 */

public class ShellSort {
    /**
     * 简单选择排序
     * @param nums
     * @return
     */
    public static void shellSort(int[] nums){
        if (nums == null || nums.length <= 0) return;
        int n = nums.length;
        for (int gap = n /2; gap > 0; gap/=2){
            for (int i = 0; i < gap ; i++){
                for (int j = i + gap; j < n; j+= gap){
                    if (nums[j] < nums[j-gap]){
                        int temp = nums[j];
                        int k = j- gap;
                        while (k >=0 && nums[k] > temp){
                            nums[k+gap] = nums[k];
                            k -= gap;
                        }
                        nums[k+gap] = temp;
                    }
                }
            }
        }
    }

    public static void test(int[] nums){
        System.out.println(String.format("before sort: %s", Arrays.toString(nums)));
        long startTime = System.nanoTime();
        shellSort(nums);
        long endTime = System.nanoTime();
        System.out.println(String.format("time :" + (endTime - startTime)+" result: %s", Arrays.toString(nums)));
    }


    public static void main(String[] args){
        test(new int[]{9,8,7,6,5,4,3,2,1});
    }
}
