package com.yumodev.java.airthmetic.offer;

/**
 * Created by yumodev on 16/10/14.
 */
public class Other {

    public static void main(String[] args){
        //new Fibonacci().testFibonacci();

        //new Power().testPower();

        new PrintMaxOfDigits().testPrintMaxOfDigits();
    }

    /****************************************
     剑指Offer9 斐波那契数列
     题目一,写一个函数,输入n,求斐波那契数列的第n项.
     *****************************************/

    public static class Fibonacci{

        public void testFibonacci(){
            System.out.println(fibonacci1(45));
            System.out.println(fibonacci(45));

        }

        public long fibonacci(int n){
            if (n == 0){
                return  0;
            }

            if (n == 1){
                return 1;
            }

            return fibonacci(n -1) + fibonacci(n -2);
        }

        public long fibonacci1(int n){
            if (n <= 1){
                return n;
            }

            long num1 = 0;
            long num2 = 1;
            long temp = 0;

            for (int i = 2 ; i < n; i++){
                temp = num2;
                num2 = num1 + num2;
                num1 = temp;
            }

            return num1 + num2;
        }
    }



    /****************************************
     剑指Offer11 数值的整数次方
     题目一:实现函数 double Power(double base, int exponent)
     求base的exponent次方,不等使用库函数,不需要考虑大数据问题
     *****************************************/

     public static class Power{
        public void testPower(){
            System.out.println(power(10,2));
            System.out.println(power(10,-2));
        }


        private double power1(double base, int exponent){
            double result = 1.0;
            while (exponent > 0){
                result *= base;
                exponent --;
            }

            return result;
        }

        private double power2(double base, int exponent){
            if (exponent == 0){
                return 1;
            }

            if (exponent == 1){
                return base;
            }

            double result = power(base, exponent >> 1);
            result *= result;

            if ((exponent & 0x1) == 1){
                result *= base;
            }

            return result;
        }

        private double power(double base, int exponent){
            if (base == 0 && exponent ==0){
                throw new IllegalArgumentException("arg is bug");
            }

            if (exponent == 0){
                return 1;
            }

            int exp = exponent;
            if (exp < 0){
                exp = - exponent;
            }

            double result = power2(base, exp);
            if (exponent < 0){
                result = 1.0 / result;
            }

            return result;
        }
    }

    /****************************************
     剑指Offer12 打印1到最大的n位数
     题目一:输入数字n, 按顺序打印出从1到最大的n位十进制数.
     比如输入3,则打印出1,2,3 一直到最大的999.
     *****************************************/

    public static class PrintMaxOfDigits{
        public void testPrintMaxOfDigits(){
            printMaxOfDigits(3);
        }

        /**
         * 基本实现, 大数据处理无效.
         * @param n
         */
        private void printMaxOfDigits(int n){
            if (n < 1){
                throw new IllegalArgumentException("args is bug");
            }

            for (int i = 1 ; i < Math.pow(10, n); i++){
                System.out.print(i + " ");
            }
        }
    }

}
