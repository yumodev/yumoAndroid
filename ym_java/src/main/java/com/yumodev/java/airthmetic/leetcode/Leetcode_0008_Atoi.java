package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 8/22/16.
 */
public class Leetcode_0008_Atoi {
    public static int myAtoi(String str) {
        if (str == null ||str.trim().length() == 0){
            return  0;
        }

        int index = 0;
        while ((index < str.length()) && (str.charAt(index) == ' ')){
            index ++;

        }

        int sign = 1;
        if (str.charAt(index) == '+'){
            sign = 1;
            index ++;
        }else if (str.charAt(index) == '-'){
            sign = -1;
            index ++;
        }

        long result = 0L;
        for (int i = index; i < str.length(); i++){
           if( str.charAt(i) >=  '0' && str.charAt(i) <= '9'){
                result = result * 10 + (str.charAt(i) - '0');
               if (sign == 1){
                   if(result > Integer.MAX_VALUE){
                       result = Integer.MAX_VALUE;
                       break;
                   }
               }else{
                   if (result*sign < Integer.MIN_VALUE){
                       result = Integer.MIN_VALUE;
                       break;
                   }
               }
           }else{
               break;
           }
        }

        return (int)result * sign;
    }

    public static void main(String[] args) {
        //String str = "  -214748364734dd";
        String str = "  -00134";
        long startTime = System.nanoTime();
        int result = myAtoi(str);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("reverse:" + result + " time:" + time);
    }
}
