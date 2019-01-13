package com.yumodev.java.airthmetic.offer;

/**
 * Created by yumodev on 16/10/15.
 */
public class TestBit {

    public static void main(String[] args){
        new NumberOf1().testNumberOf1();
    }

    /*****************************************
     剑指Offer10  二进制中1的个数
     题目:请实现一个函数,输入一个整数,
     输出该数二进制标识中1的个数.例如把9表示成二进制1001,有2位是1,因为输入9,该函数输出2.

     *****************************************/

    public static class NumberOf1{

        public void testNumberOf1(){
            System.out.println(numberOf1(9));
            System.out.println(numberOf2(9));
            System.out.println(numberOf3(9));
        }

        public int numberOf1(int n){
            int count  = 0;
            while (n > 0){
                if ((n & 1) == 1){
                    count++;
                }

                n = n>>1;
            }

            return count;
        }

        public int numberOf2(int n){
            int count  = 0;
            int flag = 1;
            while (flag > 0){
                if ((n & flag) == flag) {
                    count++;
                }

                flag = flag << 1;
            }

            return count;
        }

        public int numberOf3(int n){
            int count =0;
            while (n != 0){
                count ++;
                n = (n-1) & n;
            }

            return count;
        }
    }

}
