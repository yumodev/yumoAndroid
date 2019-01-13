package com.yumodev.java.airthmetic.leetcode;

import java.util.Arrays;

/**
 * Created by yumodev on 17/4/5.
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

 (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

 You are given a target value to search. If found in the array return its index, otherwise return -1.

 You may assume no duplicate exists in the array.

 Subscribe to see which companies asked this question.
 */

public class Leetcode_0033_search_in_rotated_sorted_array {

    /**
     * 最笨的方法,循环遍历。
     * @param nums
     * @param target
     * @return
     */
    public static int search(int[] nums, int target) {
        if (nums == null || nums.length < 1){
            return -1;
        }

        int result = -1;
        for (int i = 0; i < nums.length; i++){
            if (nums[i] == target){
                result = i;
                break;
            }
        }

        return result;
    }

    public static int search1(int[] nums, int target){
        if (nums == null || nums.length < 1){
            return -1;
        }
        int result = -1;
        int begin = 0;
        int end = nums.length -1;

        while (begin <= end){
            int mid = (begin + end) / 2;
            if (nums[mid] == target){
                result = mid;
                break;
            }else if (nums[mid] < nums[end]){
                if (target > nums[mid] && target <= nums[end]){
                    begin = mid + 1;
                }else{
                    end = mid -1;
                }
            }else{
                if (target >= nums[begin] && target<nums[mid]){
                    end = mid -1;
                }else{
                    begin = mid + 1;
                }
            }
        }
        return result;
    }

    public static void test(int[] nums, int target){
        long startTime = System.nanoTime();
        int result = search1(nums, target);
        long endTime = System.nanoTime();
        System.out.println(String.format("time :" + (endTime - startTime)+" result: %s", result));
    }


    public static void main(String[] args){
        test(new int[]{3, 5, 1}, 3); // 0
        test(new int[]{4, 5, 6, 7, 0, 1, 2}, 1); //5
        test(new int[]{1}, 1); // 0
    }
}
