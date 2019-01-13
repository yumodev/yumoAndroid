package com.yumodev.java.airthmetic.leetcode;

import java.util.Stack;

/**
 * Created by yumodev on 9/13/16.
 */
public class Leetcode_0020_ValidParentheses {
    public static boolean isValid(String s) {
        if (s == null ||s.length() <= 1){
            return false;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i <= s.length()-1;i++){
            Character ch = s.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{'){
                stack.push(ch);
            }else{
                if (stack.size() == 0){
                    return false;
                }

                Character sch = stack.pop();

                if (ch == ')'){
                    if (sch != '('){
                        return false;
                    }
                }else if (ch == ']'){
                    if (sch != '['){
                        return false;
                    }
                }else if (ch == '}'){
                    if (sch != '{'){
                        return false;
                    }
                }
            }
        }
        return stack.size() == 0;
    }

    public static void main(String[] args) {
        String[] str = {"()[]{}", "()", "(("};
        for (int i = 0; i < str.length; i++){
            long startTime = System.nanoTime();
            boolean result = isValid(str[i]);
            long endTime = System.nanoTime();
            long time = endTime - startTime;

            System.out.println(str[i]+" isValid:" + result + " time:" + time);
        }

    }
}
