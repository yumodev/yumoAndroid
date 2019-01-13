package com.yumodev.java.airthmetic.sort;

import com.yumodev.java.utils.YmStringUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by wks on 7/1/16.
 */
public class TestSort {
    private final String LOG_TAG = "TestSort";

    /******************
     * 冒泡排序
     ******************/
    public void testBubbleSort() {
        int[] ran = YmStringUtil.randomIntArray(10);
        bubbleSort(ran);
        System.out.println("冒泡排序后输出数据为："+ YmStringUtil.convertIntArrayToString(ran));
    }


    /**
     * 冒泡排序
     * yumo
     * void
     * 2015-1-5
     */
    private void bubbleSort(int ran[]) {
        System.out.println("排序前打印数据："+ YmStringUtil.convertIntArrayToString(ran));
        int temp;
        boolean bFlag = true;

        for (int i = 0; i < ran.length && bFlag; i++) {
            bFlag = false;

            for (int j = 0; j < ran.length - 1; j++) {

                if (ran[j] > ran[j + 1]) {
                    temp = ran[j + 1];
                    ran[j + 1] = ran[j];
                    ran[j] = temp;
                    bFlag = true;
                }
            }
        }

        System.out.println("排序后输出数据为："+ YmStringUtil.convertIntArrayToString(ran));
    }

    /************************************/
    /************************************/
    /******************
     * 快速排序
     ******************/

    public void tesTastSort() {
        int[] ran = YmStringUtil.randomIntArray(10);
        System.out.println( "快速排序前输出数据为："+ YmStringUtil.convertIntArrayToString(ran));
        fastSort(ran, 0, ran.length - 1);
        System.out.println("快速排序后输出数据为："+ YmStringUtil.convertIntArrayToString(ran));
    }

    public void fastSort(int[] ran, int low, int high) {
        int index = partition(ran, low, high);
        if (low < index - 1) {
            fastSort(ran, low, index - 1);
        }

        if (index < high) {
            fastSort(ran, index, high);
        }

    }

    public int partition(int[] ran, int low, int hight) {
        int pivot = ran[(low + hight) / 2]; //取中位数位基准点
        while (low <= hight) {
            while (ran[low] < pivot) low++;

            while (ran[hight] > pivot) hight--;

            if (low <= hight) {
                int temp = ran[low];
                ran[low] = ran[hight];
                ran[hight] = temp;

                low++;
                hight--;
            }
        }
        return low;
    }


    /************************************/
    /************************************/


    /************************************/
    //给定两个有序的数据A，和B，假设A有足够的空间容纳B中的元素，编写一个方法将B合并插入A中，并排序。
    /************************************/
    public void TestMerageOne() {
        int B[] = {30, 50, 60, 71, 80, 100, 101, 102, 103, 104};
        int[] A = new int[20];
        Random random = new Random();
        int num = 10;
        System.out.println("合并前");

        for (int n = 0; n < num; n++) {
            A[n] = random.nextInt(100);
        }

        fastSort(A, 0, num - 1);

        for (int n = 0; n < num; n++) {
            System.out.print(A[n] + " ");
        }

        MerageOne(A, B, num, num);
        System.out.println("合并后");

        for (int n = 0; n < num + 10; n++) {
            System.out.print(A[n] + " ");
        }
        System.out.println(" ");

    }

    public void MerageOne(int[] A, int[] B, int LenA, int LenB) {
        int nLenMerage = LenA + LenB;

        while (LenA > 0 && LenB > 0) {
            if (A[LenA - 1] > B[LenB - 1]) {
                A[nLenMerage - 1] = A[LenA - 1];
                LenA--;
            } else {
                A[nLenMerage - 1] = B[LenB - 1];
                LenB--;
            }

            nLenMerage--;
        }

        //如果B还没有合并完。
        while (LenB > 0) {
            A[LenB - 1] = B[LenB - 1];
        }
    }


    /************************************/
	/*编写一个方法，对字符串数组进行排序，，将所有变位词排在相邻为止*/

    /************************************/
    public void sortStringArrayTest() {
        String[] array = {"abcde", "cca", "abc", "cdeab", "cba", "cac"};

        //SortStringArrayTwo(array);
        SortStringArrayOne(array);

        System.out.println("输出变位词数组");
        for (int n = 0; n < array.length; n++) {
            System.out.print(array[n] + " ");
        }

        System.out.println();
    }

    /**
     * TODO 通过集合 compare来实现排序。
     * yumo
     *
     * @param array void
     *              2015-1-15
     */
    public void SortStringArrayOne(String[] array) {
        Arrays.sort(array, new AnagramComparator());
    }

    public class AnagramComparator implements Comparator<String> {

        @Override
        public int compare(String lhs, String rhs) {
            // TODO Auto-generated method stub

            return sortStrings(lhs).compareTo(sortStrings(rhs));
        }

    }


    /**
     * TODO 利用散列表实现排序
     * yumo
     *
     * @param array void
     *              2015-1-15
     */
    public void SortStringArrayTwo(String[] array) {
        Hashtable<String, LinkedList<String>> hashTable = new Hashtable<String, LinkedList<String>>();

        for (int n = 0; n < array.length; n++) {
            String str = sortStrings(array[n]);

            if (hashTable.containsKey(str)) {
                hashTable.get(str).push(array[n]);
                System.out.println(str);
            } else {
                LinkedList<String> list = new LinkedList<String>();
                list.push(array[n]);
                hashTable.put(str, list);
            }
        }

        int index = 0;
        for (String key : hashTable.keySet()) {
            LinkedList<String> list = hashTable.get(key);
            //System.out.println(key + list.size());
            for (String str : list) {
                //System.out.println(str);
                array[index++] = str;
            }
        }
    }

    /**
     * TODO 将字符串排序
     * yumo
     *
     * @param str
     * @return String
     * 2015-1-15
     */
    public String sortStrings(String str) {
        char[] content = str.toCharArray();
        Arrays.sort(content);
        return new String(content);
    }

    /************************************/
    /************************************/


    /************************************/
	/*给定一个排序后的数组，包含n个数据，但是这个数组已经被旋转过很多次，次数不详，请编写代码找出数组中的某个元素，可以假定数组元素原先是从小到大排列的*/

    /************************************/


    public void SearchRotateTest() {
        int A[] = {10, 15, 20, 0, 5};
        System.out.println(" 翻转有序数组查找后的为止：" + SearchRotate(A, 0, 4, 4));

    }

    public int SearchRotate(int[] arr, int low, int high, int x) {
        int mid = (low + high) / 2;
        if (arr[mid] == x) return mid;

        if (low > high) return -1;

        int result = -1;

        if (arr[low] < arr[mid]) {
            if (arr[low] < x && x < arr[mid]) {
                return SearchRotate(arr, low, mid - 1, x);
            } else {
                return SearchRotate(arr, mid + 1, high, x);
            }
        } else if (arr[low] > arr[mid]) {
            if (arr[mid] < x && x < arr[high]) {
                return SearchRotate(arr, mid + 1, high, x);
            } else {
                return SearchRotate(arr, low, mid - 1, x);
            }
        } else if (arr[low] == arr[mid]) {
            if (arr[mid] != arr[high]) {
                return SearchRotate(arr, mid + 1, high, x);
            } else {
                result = SearchRotate(arr, low, mid - 1, x);
                if (result == -1) {
                    return SearchRotate(arr, mid + 1, high, x);
                } else {
                    return result;
                }
            }
        }

        return -1;
    }

    /************************************/
	/*有个排序后的字符串数组，其中散布着一些空字符串，编写一个方法，指出给定的字符串为止。二分查找，但是要注意啊空字符位置。*/

    /************************************/

    public void searchStringArrWithEmpty() {
        String[] array = {"abcde", "abcdef", "bcd", "", "cdd", "dddd", "fffd", ""};
        System.out.println("查找字符串的位置：abcde:" + searchStringArr(array, "fffd", 0, array.length - 1));
    }

    public int searchStringArr(String[] array, String x, int low, int high) {
        if (array == null || x == null || low > high) return -1;

        int mid = (low + high) / 2;

        if (array[mid].isEmpty()) {
            int left = mid - 1;
            int right = mid + 1;

            while (true) {
                if (left < low && right > high) return -1;
                else if (right <= high && !array[right].isEmpty()) {
                    mid = right;
                    break;
                } else if (left >= low && !array[left].isEmpty()) {
                    mid = left;
                    break;
                }

                left--;
                right++;
            }

        }

        if (array[mid].compareTo(x) == 0) return mid;

        else if (array[mid].compareTo(x) > 0) {
            return searchStringArr(array, x, low, mid - 1);
        } else {
            return searchStringArr(array, x, mid + 1, high);
        }
    }


    /************************************/
	/*给定矩阵，每一行和每一列都按照升序排列，请编写代码，找出某元素*/

    /************************************/

    public void TestMatrix() {
        int[][] matrix = {{15, 20, 40, 85},
                {19, 30, 50, 95},
                {25, 55, 60, 105},
                {35, 65, 70, 110}};

        int[] result = recursionFindElement(matrix, 56);
        System.out.println(String.format("第%d行%d列", result[0], result[1]));
    }

    public int[] recursionFindElement(int[][] matrix, int element) {
        int row = 0;
        int col = matrix[0].length - 1;
        while (row <= matrix.length && col > 0) {
            if (matrix[row][col] == element) {
                int[] result = new int[2];
                result[0] = row + 1;
                result[1] = col + 1;

                return result;
            } else if (matrix[row][col] > element) {
                col--;
                continue;
            } else if (matrix[row][col] < element) {
                row++;
            }
        }

        return new int[2];
    }
}
