package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 8/16/16.
 */
public class Leetcode_0006_ZigZag {

    public static String convert(String s, int numRows){
        if (numRows <= 1 || s == null || s.length() == 0){
            return s;
        }

        StringBuilder sb = new StringBuilder();
        int size = 2 * numRows -2;
        for (int i = 0; i < numRows; i++){
            for (int j = i; j < s.length();j+= size){
                sb.append(s.charAt(j));
                if (i != 0 && i != numRows -1){
                    int temp = j + size - 2 * i;
                    if (temp < s.length()){
                        sb.append(s.charAt(temp));
                    }
                }
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
       // String str = "PAYPALISHIRING";
        String str = "A";
        int nRows = 1;
        long startTime = System.nanoTime();
        String zigZag = convert(str, nRows);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("ZigZag:" + zigZag + " time:" + time);
    }
}
