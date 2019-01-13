package com.yumodev.java.airthmetic.sort;

import java.util.Arrays;

/**
 * Created by yumodev on 17/6/24.
 * 冒泡排序
 * 1. 外循环是遍历每一个元素，执行第二步运算
 * 2、内循环依次比较相邻的元素。如果第一个比第二个大，就交换元素位置。
 * 3、第一步中元素遍历完毕的时候，算法结束
 *
 * 冒泡排序通过不断的交换将最大的元素慢慢浮到序列的顶端。
 *
 * 稳定排序
 *
 * 最好的情况是：只有
 */

public class BubbleSort {
    /**
     * 简单优化的冒泡排序
     * 最好情况：交换0次，比较次数：n-1,...,1 = (n-1)*(n-1+1)/2 = n(n-1)/2
     * 最坏情况：比较测试为n(n-1)/2,交换次数为：n-1...1 = n(n-1)/2 = n(n-1) 平均时间复杂度为O(n^2)
     * @param nums
     * @return
     */
    public static void bubbleSort(int[] nums){
        if (nums == null || nums.length <= 1) return;
        int temp;
        for (int i = 1; i < nums.length; i++){
            for (int j = 0 ; j < nums.length - i; j++){
                if (nums[j] > nums[j + 1]){
                    temp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = temp;
                }
            }
        }
    }

    /**
     * 冒泡排序的优化
     * 设置一个标志位flag标志是否发生数据交换。
     * 当发生了交换设置为true；
     * 在内循环结束时检查flag是否为true，如果为false
     * 说明已经排好序了。
     * @param nums
     */
    public static void bubbleSort1(int[] nums){
        if (nums == null || nums.length <= 1) return;
        int temp;
        boolean flag = false;
        for (int i = 1; i < nums.length; i++){
            for (int j = 0 ; j < nums.length - i; j++){
                if (nums[j] > nums[j + 1]){
                    flag = true;
                    temp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = temp;
                }
            }

            if (!flag){
                break;
            }
        }
    }

    /**
     * 定义一个变量swapPos，记录最后一次交换的位置，该位置后面的序列已排序完毕。
     * 当下次内循环时，swapPos就是结束内循环的位置。
     * 当swapPos = 0时，表示排序结束。
     * @param nums
     */
    public static void bubbleSort2(int[] nums){
        if (nums == null || nums.length <= 1) return;

        int temp;
        int swapPos = nums.length;
        for (int i = 0; i < nums.length -1 && swapPos != 0; i++){
            int temSwap = swapPos;
            swapPos = 0;
            for (int j = 0 ; j < temSwap -1; j++){
                if (nums[j] > nums[j + 1]){
                    swapPos = j + 1;
                    temp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = temp;
                }
            }
        }
    }

    public static void test(int[] nums){
        System.out.println(String.format("before sort: %s", Arrays.toString(nums)));
        long startTime = System.nanoTime();
        bubbleSort2(nums);
        long endTime = System.nanoTime();
        System.out.println(String.format("time :" + (endTime - startTime)+" result: %s", Arrays.toString(nums)));
    }


    public static void main(String[] args){
        test(new int[]{10,9,8,7,6,5,4,3,2,1});
    }
}
