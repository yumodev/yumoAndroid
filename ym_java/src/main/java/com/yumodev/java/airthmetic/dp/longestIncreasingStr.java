package com.yumodev.java.airthmetic.dp;

/**
 * Created by trunx on 8/8/16.
 * 一个序列有N个数：A[1],A[2],…,A[N]，求出最长非降子序列的长度
 * longest increasing subsequence)
 */
public class longestIncreasingStr {

    /**
     * 动态规划算法
     * @param A
     * @return
     */
    public static int getLongestIncreasing(int[] A){
        if (A == null || A.length == 0){
            return 0;
        }

        if (A.length == 1){
            return 1;
        }
        int[] d = new int[A.length];
        d[0] = 1;
        int max = 0;

        for (int i = 1; i < A.length; i++){
            d[i] = 1;
            for (int j = 0; j < i;j++){
                if(A[j]<=A[i] && d[j]+1>d[i]){
                    d[i] = d[j] + 1;
                }

                if(d[i] > max) max = d[i];
            }
        }

        return max;
    }

    public static void main(String[] args){
        int[] nums = {2,1,5,3,6,4,8,9,7};
        long startTime = System.nanoTime();
        int longestIncreasing = getLongestIncreasing(nums);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("longestIncreasing:" + longestIncreasing + " time:" + time);
    }
}
