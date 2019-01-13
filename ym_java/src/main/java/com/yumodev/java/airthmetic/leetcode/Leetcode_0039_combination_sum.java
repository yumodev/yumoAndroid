package com.yumodev.java.airthmetic.leetcode;


import java.util.Arrays;
import java.util.List;

import java.util.LinkedList;

/**
 * Given a set of candidate numbers (C) (without duplicates) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.

 The same repeated number may be chosen from C unlimited number of times.

 Note:
 All numbers (including target) will be positive integers.
 The solution set must not contain duplicate combinations.
 For example, given candidate set [2, 3, 6, 7] and target 7,
 A solution set is:
 [
 [7],
 [2, 2, 3]
 ]
 */

public class Leetcode_0039_combination_sum {

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null){
            return null;
        }

        Arrays.sort(candidates);
        List<List<Integer>> result = new LinkedList<List<Integer>>();
        for (int i = 0; i < candidates.length; i++) {
            int num = 1;

            while (true) {
                int temp = candidates[i] * num;
                if (temp == target) {
                    LinkedList<Integer> list = new LinkedList<>();
                    for (int n = 0; n < num; n++){
                        list.add(candidates[i]);
                    }
                    result.add(list);
                } else if (temp < target) {
                    for (int j = i + 1; j < candidates.length; j++) {
                        if (candidates[i] * num + candidates[j] == target){
                            LinkedList<Integer> list = new LinkedList<>();
                            for (int n = 0; n < num; n++){
                                list.add(candidates[i]);
                            }
                            list.add(candidates[j]);
                            result.add(list);
                        }
                    }
                } else if (temp > target) {
                    break;
                }
                num++;
            }
        }

        return result;
    }

    /**
     * 递归的方式解决
     * @param candidates
     * @param target
     * @return
     */
    public static List<List<Integer>> combinationSum_recursion(int[] candidates, int target) {
        if (candidates == null){
            return null;
        }
        Arrays.sort(candidates);
        List<List<Integer>> result = new LinkedList<>();
        List<Integer> list = new LinkedList<>();
        dfs(result, candidates, target, 0, 0, list);
        return result;
    }

    private static void dfs(List<List<Integer>> result, int[] candidates, int target, int sum, int index, List<Integer> list){
        if (sum == target){
            result.add(new LinkedList<>(list));
            return;
        }else if (sum > target){
            return;
        }else{
            for (int i = index ; i < candidates.length; i++){
                list.add(candidates[i]);
                dfs(result, candidates, target, sum + candidates[i], i, list);
                list.remove(list.size() -1);
            }
        }
    }
    public static void test(int[] nums, int target){
        long startTime = System.nanoTime();
        List<List<Integer>> result = combinationSum_recursion(nums, target);
        long endTime = System.nanoTime();
        System.out.println(String.format("time :" + (endTime - startTime)+" result: %s", result));
    }


    public static void main(String[] args){
        test(new int[]{2,3,6,7}, 7); //[{2,2,3},{7}]
        test(new int[]{1,2}, 3);//[{1,1,1},[1,2]]
        test(new int[]{2,3,5}, 8);//[{2,2,2,2},[2,3,3],[3,5]]
    }
}
