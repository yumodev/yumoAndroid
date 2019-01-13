package com.yumodev.java.airthmetic.sort;

import java.util.Arrays;

/**
 * Created by yumodev on 17/11/13.
 * 折半插入排序。是直接插入的优化。
 * 直接插入：寻找插入位置的时候，从后向前依次比较，直到找到插入位置。
 * 折半插入排序：在寻找插入的位置的时候，用low和high标识已排序序列的起止索引，先找到已排序的中间位置为nums[low+high/2],如果比中间位置大，
 * 则在nums[mid+1...high]区间内查找，如果比中间值小就在nums[low...mid-1]的区间序列中查找，一直找到合适的插入位置。
 * 折半插入和直接插入的区别仅在于查找插入位置的方式。
 */

public class InsertSort {

    /**
     * 使用for循环的直接插入。
     * 最好情况：比较n-1次
     * 最坏情况：比较n * (n-1)/2次，移动
     * @param nums
     * @return
     */
    public static void insertSort(int[] nums){
        if (nums == null || nums.length <= 1) return;

        for (int i = 1; i < nums.length; i++){
            int temp = nums[i];
            int j = i;
            for (; j > 0 && nums[j] >= temp; j--){
                nums[j] = nums[j - 1];
            }
            nums[j] = temp;
        }
    }



    /**
     * 使用While循环进行排序
     * @param nums
     */
    public static void insertSort1(int[] nums) {
        if (nums == null || nums.length <= 1) return;

        for (int i = 1; i < nums.length; i++) {
            int temp = nums[i];
            int j = i;
            while (j > 0 && nums[j] >= temp){
                nums[j] = nums[j-1];
                j--;
            }

            nums[j] = temp;
        }
    }

    public static void test(int[] nums){
        System.out.println(String.format("before sort: %s", Arrays.toString(nums)));
        long startTime = System.nanoTime();
        insertSort(nums);
        long endTime = System.nanoTime();
        System.out.println(String.format("time :" + (endTime - startTime)+" result: %s", Arrays.toString(nums)));
    }


    public static void main(String[] args){
        test(new int[]{10,9,8,7,6,5,4,3,2,1});
    }
}
