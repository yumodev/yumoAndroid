package com.yumodev.java.airthmetic.offer;

/**
 * Created by yumodev on 16/10/14.
 */
public class TestString {

    public static void main(String[] args){
        new ReplaceBlank().testReplaceBlank();
    }

    /*****************************************
     剑指Offer4 替换空格
     题目:请实现一个函数,把字符串中的每个空格替换成"%20".
     例如输入"we are happy", 则输出"We%20are%20happy"
     *****************************************/
    public static class ReplaceBlank{
        public void testReplaceBlank(){
            String str = "we are happy";
            char[] arr = replaceBlank(str.toCharArray());
            System.out.println(new String(arr));
        }

        public char[] replaceBlank(char[] arr){
            if (arr == null){
                throw new IllegalArgumentException("arg should not be null");
            }

            if (arr.length == 0){
                return arr;
            }

            int whiteCount = 0;
            for (char ch : arr){
                if (ch == ' '){
                    whiteCount++;
                }
            }

            if (whiteCount == 0){
                return arr;
            }

            char[] result = new char[arr.length + 2 * whiteCount];
            int before = 0;

            for (int i = 0; i < arr.length; i++){
                if (arr[i] != ' '){
                    result[before] = arr[i];
                    before++;
                }else{
                    result[before] = '%';
                    result[before+1] = '2';
                    result[before+2] = '0';

                    before+= 3;
                }
            }

            return result;
        }
    }
}

