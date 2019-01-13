package com.yumodev.java.airthmetic.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yumo on 8/6/16.
 * 获取字符串中不重复的字字符串的最大长度.
 */
public class Leetcode_0003_LongSubNoRepeatCHar {

    public static int lengthOfLongestSubstringByMap(String s) {
        if (s == null || s.isEmpty()){
            return 0;
        }

        Map<Character, Integer> map = new HashMap<>();
        int maxLen = 1;
        int start = 0;

        for (int i = 0; i < s.length(); i++){
            if (map.get(s.charAt(i)) != null){
                int j = map.get(s.charAt(i));
                if (j >= start){
                    maxLen = (i - start > maxLen) ? i - start : maxLen;
                    start = j + 1;
                    map.remove(s.charAt(i));
                }
            }

            map.put(s.charAt(i), i);
        }

        return (s.length() - start > maxLen) ? s.length() - start : maxLen;
    }

    public static int lengthOfLongestSubstringByArray(String s) {
        if (s == null || s.isEmpty()){
            return 0;
        }

        int[] chars = new int[256];
        int maxLen = 1;
        int start = 0;

        for (int i = 0; i < s.length(); i++){
            int ch = s.charAt(i);
            if (chars[ch] != 0){
                if (chars[ch] > start){
                    maxLen = (i - start > maxLen) ? i - start : maxLen;
                    start = chars[ch];
                }
            }

            chars[ch] = i + 1;
        }

        return (s.length() - start > maxLen) ? s.length() - start : maxLen;
    }

    public static void main(String[] args){
        //String str = "abcabcbb";
        //String str = "aaa";
        //String str = "pwwkew";
        // String str = "au";
        String str = "aab";
        long startTime = System.nanoTime();
        int length = lengthOfLongestSubstringByMap(str);
        long endTime = System.nanoTime();
        long time = endTime - startTime;
        System.out.println(str+" len:"+length +" time:"+ time);

        startTime = System.nanoTime();
        length = lengthOfLongestSubstringByArray(str);
        endTime = System.nanoTime();
        time = endTime - startTime;
        System.out.println(str+" len:"+length +" time:"+ time);
    }
}
