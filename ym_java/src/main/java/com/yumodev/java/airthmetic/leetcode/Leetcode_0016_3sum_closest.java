package com.yumodev.java.airthmetic.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yumodev on 17/3/30.
 * Given an array S of n integers, find three integers in S such that the sum is closest to a given number,
 * target. Return the sum of the three integers. You may assume that each input would have exactly one solution.
 */

public class Leetcode_0016_3sum_closest {

    /**
     * 先用最笨的方法
     * @param nums
     * @param target
     * @return
     */
    public static int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3){
            return 0;
        }

        Arrays.sort(nums);

        int temp;
        int closest = nums[0] + nums[1] + nums[2];
        Set<Integer> sets = new HashSet<>();
        sets.add(closest);
        for (int i = 0; i < nums.length-2; i++){
//            if (nums[i] >= target){
//                break;
//            }
            for(int j = i + 1; j < nums.length - 1; j++){
                for (int k = j + 1; k < nums.length; k++){
                    temp = nums[i] + nums[j] + nums[k];
                    if (temp == target){
                        closest = temp;
                        break;
                    }
                    if (sets.contains(temp)){
                        continue;
                    }
                    if (Math.abs(closest - target) > Math.abs(temp - target)){
                        closest = temp;
                    }
                    sets.add(temp);
                }

                if (closest == target){
                    break;
                }
            }

            if (closest == target){
                break;
            }
        }

        return closest;
    }


    /**
     * 先用最笨的方法
     * @param nums
     * @param target
     * @return
     */
    public static int threeSumClosest1(int[] nums, int target) {
        if (nums == null || nums.length < 3){
            return 0;
        }

        Arrays.sort(nums);

        int temp;
        int closest = nums[0] + nums[1] + nums[2];
        Set<Integer> sets = new HashSet<>();
        sets.add(closest);
        for (int i = 0; i < nums.length-2; i++){
            int begin = i + 1;
            int end = nums.length -1;
            while (begin < end){
                temp = nums[i] + nums[begin] + nums[end];
                if (temp == target){
                    closest = target;
                    break;
                }

                if (!sets.contains(temp)){
                    if (Math.abs(closest - target) > Math.abs(temp - target)){
                        closest = temp;
                    }
                    sets.add(temp);
                }

                if (temp > target){
                    end --;
                }else if (temp < target){
                    begin ++;
                }
            }

            if (closest == target){
                break;
            }
        }

        return closest;
    }

    public static void test(int[] nums, int target){
        long startTime = System.nanoTime();
        int result = threeSumClosest1(nums, target);
        long endTime = System.nanoTime();
        System.out.println(String.format("time :" + (endTime - startTime)+" result: %s", result));
    }

    public static void main(String[] args){
        test(new int[]{1,2,4,8,16,32,64,128},82);//82
        test(new int[]{-1,2,1,-4}, 1);//2
        test(new int[]{6,-18,-20,-7,-15,9,18,10,1,-20,-17,-19,-3,-5,-19,10,6,-11,1,-17,-15,6,17,-18,-3,16,19,-20,-3,-17,-15,-3,12,1,-9,4,1,12,-2,14,4,-4,19,-20,6,0,-19,18,14,1,-15,-5,14,12,-4,0,-10,6,6,-6,20,-8,-6,5,0,3,10,7,-2,17,20,12,19,-13,-1,10,-1,14,0,7,-3,10,14,14,11,0,-4,-15,-8,3,2,-5,9,10,16,-4,-3,-9,-8,-14,10,6,2,-12,-7,-16,-6,10},
        -52); //-52
        test(new int[]{43,75,-90,47,-49,72,17,-31,-68,-22,-21,-30,65,88,-75,23,97,-61,53,87,-3,33,20,51,-79,43,80,-9,34,-89,-7,93,43,55,-94,29,-32,-49,25,72,-6,35,53,63,6,-62,-96,-83,-73,66,-11,96,-90,-27,78,-51,79,35,-63,85,-82,-15,100,-82,1,-4,-41,-21,11,12,12,72,-82,-22,37,47,-18,61,60,55,22,-6,26,-60,-42,-92,68,45,-1,-26,5,-56,-1,73,92,-55,-20,-43,-56,-15,7,52,35,-90,63,41,-55,-58,46,-84,-92,17,-66,-23,96,-19,-44,77,67,-47,-48,99,51,-25,19,0,-13,-88,-10,-67,14,7,89,-69,-83,86,-70,-66,-38,-50,66,0,-67,-91,-65,83,42,70,-6,52,-21,-86,-87,-44,8,49,-76,86,-3,87,-32,81,-58,37,-55,19,-26,66,-89,-70,-69,37,0,19,-65,38,7,3,1,-96,96,-65,-52,66,5,-3,-87,-16,-96,57,-74,91,46,-79,0,-69,55,49,-96,80,83,73,56,22,58,-44,-40,-45,95,99,-97,-22,-33,-92,-51,62,20,70,90},
        284);//284
    }
}
