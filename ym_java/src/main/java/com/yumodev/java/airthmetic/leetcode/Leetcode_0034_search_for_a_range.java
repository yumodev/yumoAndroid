package com.yumodev.java.airthmetic.leetcode;


import java.util.Arrays;

/**
 * Created by yumodev on 17/4/5.
 * Given an array of integers sorted in ascending order, find the starting and ending position of a given target value.

 Your algorithm's runtime complexity must be in the order of O(log n).

 If the target is not found in the array, return [-1, -1].

 For example,
 Given [5, 7, 7, 8, 8, 10] and target value 8,
 return [3, 4].

 Subscribe to see which companies asked this question.
 *
 */

public class Leetcode_0034_search_for_a_range {

    /**
     * 使用二分查找算法。
     * @param nums
     * @param target
     * @return
     */
    public static int[] searchRange(int[] nums, int target) {
        if (nums == null){
            return new int[]{-1,-1};
        }

        int begin = 0, end = nums.length -1, beginIndex = -1, endIndex = -1;
        while (begin <= end){
            int mid = (begin + end) / 2;
            if (nums[mid] == target){
                beginIndex = mid;
                endIndex = mid;
                while (beginIndex > 0 && nums[beginIndex -1] == target){
                    beginIndex = beginIndex -1;
                }

                while (endIndex < nums.length - 1 && nums[endIndex + 1] == target){
                    endIndex = endIndex + 1;
                }
                break;
            }else if (nums[mid] < target){
                begin = mid + 1;
            }else if (nums[mid] > target){
                end = mid - 1;
            }
        }
        return new int[]{beginIndex, endIndex};
    }

    /**
     * 一种更有效率的二分查找算法。
     * @param nums
     * @param target
     * @return
     */
    public static int[] searchRange1(int[] nums, int target) {
        if (nums == null){
            return new int[]{-1,-1};
        }

        int begin = 0, end = nums.length -1, beginIndex = -1, endIndex = -1;
        while (begin <= end){
            int mid = begin + (end - begin) / 2;
            if (nums[mid] == target){
                beginIndex = mid;
                endIndex = mid;
                while (beginIndex > 0 && nums[beginIndex -1] == target){
                    beginIndex = beginIndex -1;
                }

                while (endIndex < nums.length - 1 && nums[endIndex + 1] == target){
                    endIndex = endIndex + 1;
                }
                break;
            }else if (nums[mid] < target){
                begin = mid + 1;
            }else if (nums[mid] > target){
                end = mid - 1;
            }
        }
        return new int[]{beginIndex, endIndex};
    }

    public static void test(int[] nums, int target){
        long startTime = System.nanoTime();
        int[] result = searchRange1(nums, target);
        long endTime = System.nanoTime();
        System.out.println(String.format("time :" + (endTime - startTime)+" result: [%d, %d]", result[0], result[1]));
    }


    public static void main(String[] args){
        test(new int[]{1}, 1);
        test(new int[]{2,2}, 2);
        test(new int[]{5,7,7,8,8,10}, 8); // 0
    }
}
