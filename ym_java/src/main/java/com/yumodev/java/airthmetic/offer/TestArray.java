package com.yumodev.java.airthmetic.offer;

import com.yumodev.java.airthmetic.tree.MaxHeap;

import java.util.Comparator;

/**
 * Created by yumodev on 10/10/16.
 */
public class TestArray {

    public static void main(String[] args){
        //testFind();
        //testMinNumberInRotatedArray();
        //new MoreThanHalfNum().testMoreThanHalfNum();
        //new MinNumInArray().testMinNumInArray();
        //new FindGreatestSumOfSubArray().testFindGreatestSumOfSubArray();
        //new NumberOfN().testNumberOfN();
        //new SortArrayForMinNumber().testSortArrayForMinNumber();
        //new TestUglyNumber().testUglyNumber();
        //new FirstNoteRepeatingChar().testFirstNoteRepeatingChar();
        //new InversePairs().testInversePairs();
        //new NumberOfK().testNumberOfK();
        //new NumbersAppearOnce().testNumbersAppearOnce();

        new ReorderArray().testReorderArray();
    }

    public static void printArray(int[] arr){
        StringBuilder sb = new StringBuilder(arr.length);
        for (int i : arr){
            sb.append(i + " ");
        }

        System.out.println(sb.toString());

    }

    /*****************************************
     剑指Offer3 二维数组的查找
     题目:在一个二维数组中,每一行都按照从做导游递增的顺序排序,
     每一列都按照从上到下递增的顺序排序,请完成一个函数,
     输入这样的一个二维数组和一个整数,判断数组中是否含有该整数
     *****************************************/

    public static class FindNumberInArray{

        public void testFind(){
            int[][] arr = {
                    {1,2,8,9},
                    {2,4,9,12},
                    {4,7,10,13},
                    {6,8,11,15}
            };

            System.out.print("isFind"+Find(arr, 4,4, 0));
        }

        public boolean Find(int[][] arr, int rows, int cols, int num){
            if (arr == null || rows == 0 || cols ==0 ){
                return false;
            }

            int row = 0;
            int col = cols -1;

            while (row < rows && (col < cols && col >= 0)){
                if (num == arr[row][col]){
                    return true;
                }else if (num < arr[row][col]){
                    col--;
                }else if (num > arr[row][col]){
                    row++;
                }
            }



            return false;
        }
    }



    /*****************************************
    剑指Offer8  旋转数组的最小数字
    把一个数组最开的若干个元素搬到数组的末尾,我们称之为数组的旋转.
     输入一个递增排序的数组的一个选择,输出旋转的最小元素,比如数组
     {3,4,5,1,2 } 是{1, 2, 3, 4, 5}的一个旋转,该数组的最小元素为1

    *****************************************/

    public static void testMinNumberInRotatedArray(){
        int[] arr = {1,0,1,1,1};

        System.out.println(minNumberInRotateArray(arr));

    }

    public static int minNumberInRotateArray(int[] arr){
        if (arr == null || arr.length == 0){
            throw new IllegalStateException("minNumberInRotateArray index error");
        }

        if (arr.length == 1){
            return arr[0];
        }

        int lo  = 0;
        int hi = arr.length - 1;
        int mid = lo;

        while (arr[lo] >= arr[hi]){
            if (hi - lo == 1){
                mid = hi;
                break;
            }

            mid = (lo + hi)/2;

            if (arr[lo] == arr[hi] &&arr[lo] == arr[mid]){
                int result = arr[lo];
                for (int i = lo + 1; i<= hi; i++){
                    if (result >= arr[i]){
                        result = arr[i];
                    }
                }

                return  result;
            }

            if (arr[mid] >= arr[lo]){
                lo = mid;
            }else if (arr[mid] <= arr[hi]){
                hi = mid;
            }
        }

        return arr[mid];
    }
    /*****************************************
     剑指Offer14 调整数组顺序是奇数都位于偶数的前面.
     输入一个整数数组,实现一个函数来调整该数组中数字的顺序
     是的所有的奇数都位于前半部分,所有的偶数都位于后半部分
     *****************************************/
    public static class ReorderArray{

        public void testReorderArray(){
            printArray(reorderArray(new int[]{1,2,3,4,5,6,7,8}));
        }

        private int[] reorderArray(int[] arr){
            if (arr == null){
                throw new IllegalArgumentException("args should not be null");
            }

            int lo = 0;
            int hi = arr.length -1;
            int temp;
            while (lo < hi) {
                while (arr[lo] % 2 != 0) {
                    lo++;
                }

                while (arr[hi] % 2 == 0) {
                    hi--;
                }

                if (lo < hi){
                    temp = arr[lo];
                    arr[lo] = arr[hi];
                    arr[hi] = temp;
                }
            }

            return arr;
        }
    }

    /*****************************************
     剑指Offer29 数组中出现次数超过一半的数组
     数组中有一个数字出现的册数超过数组长度的一般,请找出这个数字.
     例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}
     由于数字出现了5次,超过数组长度的一般,因此输出2.
     *****************************************/
    public static class MoreThanHalfNum{
        public  void testMoreThanHalfNum(){
            int[] arr = {1,2,3,2,2,2,5,4,2};
            //int[] arr = {1,2,3};
            //System.out.println(moreThanHalfNum(arr));

            System.out.println(moreThanHalfNumByPartition(arr));
        }

        public boolean checkValidNum(int[] arr, int num){
            int count = 0;
            for (int i = 0; i < arr.length; i++){
                if (arr[i] == num){
                    count++;
                }
            }

            if (count > arr.length/2){
                return true;
            }else{
                return false;
            }
        }

        public int moreThanHalfNum(int[] arr){
            if (arr == null || arr.length == 0){
                throw new IllegalStateException("moreThanHalfNum Invalid");
            }

            int candidate = arr[0];
            int len = arr.length;
            int nTimes, i;
            for (i = nTimes = 0; i < len; i++){
                if (nTimes == 0){
                    candidate = arr[i];
                    nTimes = 1;
                }else{
                    if (candidate == arr[i]){
                        nTimes++;
                    }else{
                        nTimes--;
                    }
                }
            }

            if (!checkValidNum(arr, candidate)){
                throw new IllegalStateException("not have valid num");
            }

            return candidate;
        }


        public int moreThanHalfNumByPartition(int[] arr){
            if (arr == null || arr.length == 0){
                throw new IllegalStateException("moreThanHalfNum Invalid");
            }
            int mid = arr.length >> 1;
            int lo = 0;
            int hi = arr.length -1;

            int index = partition(arr, lo, hi);
            while (index != mid){
                if (index > mid){
                    hi = index -1;
                    index = partition(arr, lo, hi);
                }else{
                    lo = index + 1;
                    index = partition(arr, lo, hi);
                }
            }

            int result = arr[mid];
            if (!checkValidNum(arr, result)){
                throw new IllegalStateException("not have valid num");
            }

            return result;
        }

        public int  partition(int[] arr, int lo, int hi){
            int temp = arr[lo];
            while (lo < hi){
                while (lo < hi && arr[hi] >= temp){
                    hi --;
                }

                arr[lo] = arr[hi];

                while (lo < hi && arr[lo] <= temp){
                    lo ++;
                }

                arr[hi] = arr[lo];
            }

            arr[lo] = temp;
            return lo;
        }
    }


    /*****************************************
     剑指Offer30 输入n个整数,找出其中最小的K个数,例如输入4.5,1,6,2,7,3,8
     这几个数字 最小的4个数字是1,2,3,4
     *****************************************/

    public static class MinNumInArray{
        public void testMinNumInArray(){
            int[] arr = {4,5,1,6,2,7,3,8};
            int k = 4;
            //int[] num = getMinNumInArray(arr, k);
            int[] num = getMinNumByMaxHeap(arr, k);
            for (int i = 0; i < k; i++){
                System.out.println(num[i] + " ");
            }
            System.out.println();
        }

        public int  partition(int[] arr, int lo, int hi){
            int temp = arr[lo];
            while (lo < hi){
                if (lo < hi && arr[hi] >= temp){
                    hi --;
                }

                arr[lo] = arr[hi];

                if (lo < hi && arr[lo] <= temp){
                    lo++;
                }

                arr[hi]= arr[lo];
            }

            arr[lo] = temp;
            return lo;
        }

        public int[] getMinNumInArray(int[] arr, int k){
            if (arr == null || arr.length <= k){
                return arr;
            }

            int lo = 0;
            int hi = arr.length -1;
            int index = partition(arr, lo, hi);
            while (index != k -1){
                if (index > k -1){
                    hi = index -1;
                }else{
                    lo = index + 1;

                }
                index = partition(arr, lo, hi);
            }

            int[] num = new int[k];
            for (int i = 0; i < k ; i++){
                num[i] = arr[i];
            }

            return num;
        }

        public int[] getMinNumByMaxHeap(int[] arr, int k){
            if (arr == null || arr.length <= k){
                return arr;
            }

            MaxHeap<Integer> heap = new MaxHeap<>();

            for (int i = 0; i < arr.length; i++){
                if (heap.size() < k){
                    heap.add(arr[i]);
                }else{
                    if (arr[i] < heap.first()){
                        heap.deleteTop();
                        heap.add(arr[i]);
                    }
                }
            }

            int[] num = new int[k];
            int j = 0;
            while (heap.hasNext()){
                num[j++] = heap.next();
            }

            return num;
        }
    }


    /*****************************************
     剑指Offer31 连续子数组的最大和
     输入一个整型数组,数组里有正数,也有负数.数组中一个或连续的多个整数
     组成一个子数组.求所有子数组和的最大值.要求时间复杂度为O(n).
     比如输入的数组为:{1,-2,3,10,-4,7,2,-5} 最大的子数组为{3,10,-4,7,2},和是18
     *****************************************/

    public static class FindGreatestSumOfSubArray{
        public void testFindGreatestSumOfSubArray(){
            int[] arr = {1,-2,3,10,-4,7,2,-5};
            System.out.println(findGreatestSumOfSubArray(arr));
        }

        public int findGreatestSumOfSubArray(int[] arr){
            if (arr == null){
                throw new IllegalStateException("array not null");
            }

            int sum = 0;
            int max = 0;

            for (int i =0; i < arr.length; i++){
                if (sum < 0){
                    sum = arr[i];
                }else{
                    sum += arr[i];
                }

                if (sum > max){
                    max = sum;
                }
            }

            return max;
        }
    }

    /*****************************************
     剑指Offer32 输入一个整数n, 从1到n这n个整数的十进制中1出现的次数
     题目:输入一个整数你,求从1到n这n个整数的十进制标识中1出现的次数,例如输入12,从1到12这些整数中包含1的数字有1,10,11,12, 一共出现54次.
     *****************************************/

    public static class NumberOfN{
        public void testNumberOfN(){
            int n = 12;
            System.out.println(getNumberOfN(n));
        }

        private int getNumberOfN(int n){
            if (n <= 0){
                return 0;
            }

            int count = 0;
            int factor = 1;
            int lowerNum = 0;
            int currNum = 0;
            int higherNum = 0;

            while (n/factor != 0){
                lowerNum = n % factor;
                currNum = (n / factor) % 10;
                higherNum = n / (factor * 10);

                if (currNum == 0){
                    count+= higherNum * factor;
                }else if (currNum == 1){
                    count += higherNum * factor + lowerNum + 1;
                }else{
                    count += (higherNum + 1) * factor;
                }

                factor*= 10;
            }

            return count;

        }
    }



    /*****************************************
     剑指Offer33 把数组排成最小的数
     题目:输入一个正整数数组,把数组里所有的数字拼接起来排成一个数,
     打印能拼接出所有数字中最小的一个.例如输入数组{3,32,321},
     则打印出这3个数字能排成的最小数字321323.
     *****************************************/
     public static class SortArrayForMinNumber{
        public void testSortArrayForMinNumber(){
            String[] arr = {"3", "32", "321"};
            System.out.println(sortArrayForMinNumber(arr));

            String[] arr1 = {"1", "2", "3"};
            System.out.println(sortArrayForMinNumber(arr1));

            String[] arr2 = {"2", "1", "3"};
            System.out.println(sortArrayForMinNumber(arr2));
        }

        private String sortArrayForMinNumber(String[] arr){
            if (arr == null){
                throw new IllegalArgumentException("arg not be null");
            }

            if (arr.length == 0){
                return "";
            }

            MComparator comparator = new MComparator();
            quickSort(arr, 0, arr.length -1, comparator);

            StringBuilder stringBuilder = new StringBuilder();
            for (int i =0; i < arr.length;i++){
                stringBuilder.append(arr[i]);
            }

            return stringBuilder.toString();

        }

        private void quickSort(String[] arr, int lo, int hi, MComparator comparable){
            if(lo >= hi){
                return;
            }

            int left = lo, right = hi;
            String temp = arr[lo];

            while (lo < hi){
                if (lo < hi &&  comparable.compare(arr[hi], temp) >= 0){
                    hi --;
                }

                arr[lo] = arr[hi];

                if (lo < hi && comparable.compare(arr[lo], temp) <= 0){
                    lo ++;
                }

                arr[hi] = arr[lo];
            }

            arr[lo] = temp;

            quickSort(arr, left, lo-1, comparable);
            quickSort(arr, lo+1, right, comparable );


        }


        private static class MComparator implements Comparator<String> {

            @Override
            public int compare(String o1, String o2) {
                if (o1 == null || o2 == null){
                    throw new IllegalArgumentException("arg should not be null");
                }

                String s1 = o1 + o2;
                String s2 = o2 + o1;
                return s1.compareTo(s2);
            }
        }


    }


    /*****************************************
     剑指Offer34 丑数
     题目:我们把只包含因为2,3,5的数称作丑数(UglyNumber).
     求按从小到大的顺序的弟1500个丑数.例如6,8都是丑数,但是14不是.
     因为她包含因子7,习惯上我们把1当做第一个丑数.

     一个数m是另一个数n的因子,是指n能被m整除,也就是n%m == 0
     *****************************************/

    public static class TestUglyNumber{
        public void testUglyNumber(){
            System.out.println(getUglyNumber(1));
            System.out.println(getUglyNumber(3));
            System.out.println(getUglyNumber(1500));
        }


        private int getUglyNumber(int index){
            if (index <= 0){
                return 0;
            }

            int[] nums = new int[index];
            nums[0] = 1;
            int cursor = 1;
            int p2 =0, p3 =0, p5=0;

            while (cursor < index){
                int min = min(nums[p2]*2, nums[p3]*3, nums[p5]*5);
                nums[cursor] = min;
                cursor++;
                while (nums[p2] * 2 <= min){
                    p2 ++;
                }

                while (nums[p3] * 3 <= min){
                    p3 ++;
                }

                while (nums[p5] * 5 <= min){
                    p5 ++;
                }
            }

            return nums[index-1];
        }

        private static int min(int n1, int n2, int n3){
            return Math.min(Math.min(n1,n2), n3);
        }
    }

    /*****************************************
     剑指Offer35 第一个只出现一次的字符
     题目:在字符串中找出第一个只出现一次的字符,如输入"abaccdeff",则输出b.
     *****************************************/

    public static class FirstNoteRepeatingChar{
        public void testFirstNoteRepeatingChar(){
            System.out.println(firstNoteRepeatingChar("abaccdeff"));
        }

        public char firstNoteRepeatingChar(String str){
            int[] arr = new int[256];

            for (int i = 0; i < str.length() - 1; i++){
                if (arr[str.charAt(i)] == 1){
                    arr[str.charAt(i)] = 0;
                }else if(arr[str.charAt(i)] == 0){
                    arr[str.charAt(i)] = 1;

                }
            }

            char result = 0;
            for (int i = 0; i < 256; i++){
                if (arr[i] == 1){
                    result = (char)i;
                    break;
                }
            }

            return result;
        }

    }

    /*****************************************
     剑指Offer36 数组中的逆序对
     题目:在数组中的两个数字,如果前面一个数字大于后面的数字,
     则这两个数字租场一个逆序对.输入一个数组,
     求出这个数组中的逆序对的总数.
     比如数组{7.5.6.4}中,一共有 (7.6),(7,5),(7,4),(6,4),(5,4)
     *****************************************/
    public static class InversePairs {
        public void testInversePairs(){
            System.out.println(inversePairs(new int[]{7,6,5,4}));
        }

        public int inversePairs(int[] arr){
            return 0;
        }
    }



    /*****************************************
     剑指Offer38 数字在排序数组中出现的次数.
     题目:统计一个数字在排序数组中出现的次数.例如输入的排序数组{1,2,3,3,3,4,5}和数字3
     由于3 在这个数组里面出现了4次,因此输出是4.
     *****************************************/
    public static class NumberOfK{
        public void testNumberOfK(){
            System.out.println(numberOfK(new int[]{1,2,3,3,3,3,4,5}, 3));
        }

        public int numberOfK(int[] arr, int k){
            if (arr == null){
                throw new IllegalArgumentException("array should note be null");
            }

            if (arr.length == 0){
                return 0;
            }

            int first = getFirstK(arr, 0, arr.length-1, k);
            int last = getLastK(arr, 0, arr.length-1, k);
            if (first > -1 && last > -1){
                return last - first + 1;
            }

            return 0;
        }

        private int getFirstK(int[] arr, int lo, int hi, int k){
            if (arr == null || lo > hi){
                return  -1;
            }

            int mid = lo + (hi - lo) / 2;
            if (arr[mid] == k){
                if (mid > 0 && arr[mid-1] !=k  || mid == 0){
                    return mid;
                }else{
                    hi = mid - 1;
                }

            }else if (arr[mid] > k){
                hi = mid - 1;
            }else if (arr[mid] < k){
                lo = mid + 1;
            }
            return getFirstK(arr, lo, hi, k);
        }

        private int getLastK(int[] arr, int lo, int hi, int k){
            if (arr == null || lo > hi){
                return  -1;
            }

            int mid = lo + (hi - lo) / 2;
            if (arr[mid] == k){
                if (mid + 1 < arr.length && arr[mid+1] !=k  || mid == arr.length -1){
                    return mid;
                }else{
                   lo = mid + 1;
                }

            }else if (arr[mid] > k){
                hi = mid - 1;
            }else if (arr[mid] < k){
                lo = mid + 1;
            }
            return getLastK(arr, lo, hi, k);
        }
    }

    /*****************************************
     剑指Offer40 数组中只出现一次的数字
     题目:一个整型数组里处理两个数字之外,其他的数字,都出现了两次,
     请写程序找出这两个只出现一次的数字,要求时间复杂度是O(n),
     空间复杂度O(1)
     输入数组{2,4,3,6,3,2,5,5},因为只有4,6这两个数字只出现一次,
     其他数字都出现了2次,所以输出为4和6
     *****************************************/

    public static class NumbersAppearOnce{

        public void testNumbersAppearOnce(){
            int[] arr = numbersAppearOnce(new int[]{2,4,3,6,3,2,5,5});

            if (arr.length == 0){
                System.out.println("no data");
            }else{
                for (int i = 0; i < arr.length; i++){
                    System.out.print(arr[i]+" ");
                }
            }
        }

        public int[] numbersAppearOnce(int[] arr){
            int[] result = {0,0};
            if (arr == null || arr.length < 2){
                return result;
            }

            int xor  = 0;
            for (int i : arr){
                xor ^= i;
            }

            System.out.println("xor:"+ xor);

            int index1 = findFirstBit1(xor);

            System.out.println("index of first 1: "+index1);

            for (int i : arr){
                if (isBit1(i, index1)){
                    result[0] ^= i;
                }else{
                    result[1] ^= i;
                }
            }
            return result;
        }

        private int findFirstBit1(int num){
            int index  = 0;
            while ((num & 1) == 0 && index < 32){
                num >>>= 1;
                index++;
            }

            return index;
        }

        private boolean isBit1(int num, int indexBit){
            num >>>= indexBit;
            return (num & 1) == 1;
        }
    }

    /*****************************************
     剑指Offer51 数组中重复的数字
     题目:在一个长度为n的数组里的所有数组都是在0到n-1的范围内.
     数组中的某些数字是重复的,但不知道有几个数字重复了,
     也不知道每个数字重复了几次.请找出数组中任意一个重复的数字.例如,
     如果输入长度为7的数组{2,3,1,0,2,5,3},那么对象的输出是重复的数字2和3.
     *****************************************/
    public static class DuplicationInArray{
        public void testDuplicationInArray(){

        }

        public int[] duplicationInArray(int[] arr){
            return null;
        }
    }

    /*****************************************
     剑指Offer52 构建乘积数组
     题目:给定一个数组A[0,1,...n-1],请够爱金一个数组B[0,1,...,n-1],
     其中B中的元素B[i] = A[0]*A[1]*A[i-1]*A[i+1]*...*A[n-1]
     *****************************************/
    public static class ArrayConstruction{
        public void testArrayConstruction(){

        }

        public int[] arrayConstruction(int arr){
            return null;
        }
    }
}
