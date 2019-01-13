package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 17/4/5.
 Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

 You may assume no duplicates in the array.

 Here are few examples.
 [1,3,5,6], 5 → 2
 [1,3,5,6], 2 → 1
 [1,3,5,6], 7 → 4
 [1,3,5,6], 0 → 0

 Subscribe to see which companies asked this question.
 */

public class Leetcode_0035_search_insert_position {

    /**
     * 最笨的方法,循环遍历。
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert(int[] nums, int target) {
        if (nums == null){
            return 0;
        }

        int begin = 0;
        int end = nums.length -1;
        while (begin <= end){
            int mid = (begin + end) / 2;
            if (nums[mid] == target ){
                return mid;
            }else if (nums[mid] < target){
                begin = mid + 1;
                if (begin < nums.length && nums[begin] > target){
                    return begin;
                }
            }else{
                end = mid -1;
                if (end >= 0 && nums[end] < target){
                    return end + 1;
                }
            }
        }

       return end + 1;
    }


    public static void test(int[] nums, int target){
        long startTime = System.nanoTime();
        int result = searchInsert(nums, target);
        long endTime = System.nanoTime();
        System.out.println(String.format("time :" + (endTime - startTime)+" result: %s", result));
    }


    public static void main(String[] args){
        test(new int[]{1,3}, 2); // 1
        test(new int[]{1,3}, 0); // 0
        test(new int[]{1,3,5,6}, 5); // 2
        test(new int[]{1,3,5,6}, 2); // 1
        test(new int[]{1,3,5,6}, 7); // 4
        test(new int[]{1,3,5,6}, 0); // 0
    }
}
