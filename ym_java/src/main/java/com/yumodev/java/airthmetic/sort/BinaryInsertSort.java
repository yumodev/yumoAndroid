package com.yumodev.java.airthmetic.sort;

import java.util.Arrays;

/**
 * Created by yumodev on 17/11/16.
 * 折半插入排序
 */

public class BinaryInsertSort {
    /**
     * 使用for循环的直接插入。
     * @param nums
     * @return
     */
    public static void binaryInsertSort(int[] nums){
        if (nums == null || nums.length <= 1) return;

        for (int i = 1; i < nums.length; i++){
            int low = 0;
            int high = i - 1;

            int temp = nums[i];
            while (low <= high){
                int mid = (low + high )/2;
                if (temp < nums[mid]){
                    low = mid + 1;
                }else{
                    high = mid - 1;
                }
            }

            int j = i - 1;
            for (; j >= low; --j){
                nums[j + 1] = nums[j];
            }

            nums[low] = temp;
        }
    }
    public static void test(int[] nums){
        System.out.println(String.format("before sort: %s", Arrays.toString(nums)));
        long startTime = System.nanoTime();
        binaryInsertSort(nums);
        long endTime = System.nanoTime();
        System.out.println(String.format("time :" + (endTime - startTime)+" result: %s", Arrays.toString(nums)));
    }


    public static void main(String[] args){
        test(new int[]{10,9,8,7,6,5,4,3,2,1});
    }
}
