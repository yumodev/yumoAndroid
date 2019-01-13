package com.yumodev.java.airthmetic.sort;

import java.util.Arrays;

/**
 * Created by yumodev on 17/6/19.
 * 简单选择排序
 * 1. 选择待排序序列中选择最小的元素，然后和待排序序列的第一个元素交换位置
 * 2. 将剩下的待排序序列中选择最小的元素，和待排序序列的第二个元素交换位置
 * 3. 重复以上步骤，直到将整个数组都排序。
 *
 * 因为在不断的选择剩余最小的元素，所以称之为选择排序
 *
 * 简单选择排序特点交换移动次数比较少，比较次数和数组本身是否排序无关。
 * 比较n(n-1)/2次比较。
 * 最好的情况下不需要交换，为0次, 最坏的情况下，进行n-1次交换。
 *
 * (n-1), (n-2),,,,0 = (n-1+0) * n /2 次
 */

public class SelectSort {

    /**
     * 简单选择排序
     * @param nums
     * @return
     */
    public static void selectSort(int[] nums){
        if (nums == null || nums.length <= 0) return;

        int minIndex, temp;
        for (int i = 0; i < nums.length ; i++){
            minIndex = i;
            for (int j = i + 1; j < nums.length; j++){
                if (nums[j] < nums[minIndex]){
                    minIndex = j;
                }
            }

            temp = nums[minIndex];
            nums[minIndex] = nums[i];
            nums[i] = temp;
        }
    }

    public static void test(int[] nums){
        System.out.println(String.format("before sort: %s", Arrays.toString(nums)));
        long startTime = System.nanoTime();
        selectSort(nums);
        long endTime = System.nanoTime();
        System.out.println(String.format("time :" + (endTime - startTime)+" result: %s", Arrays.toString(nums)));
    }


    public static void main(String[] args){
        test(new int[]{4,3,2,1});
    }
}
