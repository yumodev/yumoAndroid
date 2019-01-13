package com.yumodev.java.airthmetic.dp;

import java.util.Scanner;

/**
 * Created by yumodev on 8/8/16.
 * 收集苹果
 */
public class CollectApple {

    public static int getAppleMax(int[][] nums, int m, int n){
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                if (i == 0){
                    if (j != 0){
                        dp[i][j] = nums[i][j]+dp[i][j-1];
                    }else{
                        dp[i][j] = nums[i][j];
                    }
                }else if (j == 0){
                    dp[i][j] = nums[i][j] + dp[i-1][j];
                }else{
                    dp[i][j] = nums[i][j] + Math.max(dp[i][j-1] , dp[i-1][j]);
                }
            }
        }

        System.out.println("计算后");
        printNums(dp, m, n);
        return dp[m-1][n-1];
    }

    private static void printNums(int[][] nums, int m, int n){
        for (int i = 0; i < m ; i++){
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < n; j++){
                sb.append(nums[i][j]+"   ");
            }
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args){
        int m = 1;
        int n = 1;

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入格子的行和列");

        m = scanner.nextInt();
        n = scanner.nextInt();

        int[][] nums = new int[m][n];
        System.out.println("请输入"+m*n+"个数字");

        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                nums[i][j] = scanner.nextInt();
            }
        }

        printNums(nums, m, n);

        long startTime = System.nanoTime();
        int longestIncreasing = getAppleMax(nums, m, n);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("longestIncreasing:" + longestIncreasing + " time:" + time);
    }
}
