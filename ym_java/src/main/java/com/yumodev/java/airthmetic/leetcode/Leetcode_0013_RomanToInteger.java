package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 9/12/16.
 */
public class Leetcode_0013_RomanToInteger {
    public static int romanToInt(String s) {
        if (s == null || s.length() == 0){
            return 0;
        }

        int result = 0;
        for (int i = 0; i < s.length();i++){
            char ch = s.charAt(i);
            switch (ch){
                case 'I':{
                    if (i < s.length() -1  && s.charAt(i + 1) != 'I'){
                        result -= 1;
                    }else{
                        result += 1;
                    }
                    break;
                }
                case 'V':{
                    if (i < s.length() -1  && (s.charAt(i + 1) != 'I' && s.charAt(i + 1) != 'V')){
                        result -= 5;
                    }else{
                        result += 5;
                    }
                    break;
                }
                case 'X':{
                    if (i < s.length() -1  && ( s.charAt(i + 1) == 'C' || s.charAt(i + 1) == 'L' || s.charAt(i + 1) == 'M' || s.charAt(i + 1) == 'D')){
                        result -= 10;
                    }else{
                        result += 10;
                    }
                    break;
                }
                case 'L':{
                    if (i < s.length() -1  && ( s.charAt(i + 1) == 'C' || s.charAt(i + 1) == 'M' || s.charAt(i + 1) == 'D')){
                        result -= 50;
                    }else{
                        result += 50;
                    }
                    break;
                }
                case 'C':{
                    if (i < s.length() -1  && (s.charAt(i + 1) == 'M' || s.charAt(i + 1) == 'D')){
                        result -= 100;
                    }else{
                        result += 100;
                    }
                    break;
                }
                case 'D':{
                    if (i < s.length() -1  && s.charAt(i + 1) == 'M'){
                        result -= 500;
                    }else{
                        result += 500;
                    }
                    break;
                }
                case 'M':{
                    result += 1000;
                    break;
                }

            }

        }

        return result;
    }

    public static void main(String[] args) {
        String str = "MDCCCLXXXIV";
        long startTime = System.nanoTime();
        int result = romanToInt(str);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        System.out.println("romanToInt:" + result + " time:" + time);
    }
}
