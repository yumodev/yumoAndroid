package com.yumodev.java.airthmetic.leetcode;

/**
 * Created by yumodev on 8/7/16.
 */
public class Leetcode_0004_MedianTwoSortedArrays {

    /**
     * 通过合并的方式进行字符串处理.
     */
    public static double findMedianSortedArraysByMerge(int[] nums1, int[] nums2) {
        if (nums1.length == 0 && nums2.length == 0) return 0;
        int leftMedian = (nums1.length+nums2.length+1)/2;
        int rightMedian = (nums1.length+nums2.length+2)/2;
        if (nums1.length == 0){
            return (nums2[leftMedian - 1]+ nums2[rightMedian - 1]) / 2.0;
        }else if(nums2.length == 0){
            return (nums1[leftMedian - 1]+ nums1[rightMedian - 1]) / 2.0;
        }

        int num = 0, firstNum = 0;
        int n1 = 0, n2 = 0, index = 1;
        while (true){
            if (n1 < nums1.length){
                if (n2 < nums2.length){
                    if(nums1[n1] > nums2[n2]){
                        num = nums2[n2++];
                    }else{
                        num = nums1[n1++];
                    }
                }else{
                    num = nums1[n1++];
                }
            }else{
                num = nums2[n2++];
            }

            if (index == leftMedian && rightMedian == leftMedian){
                return num;
            }else if (index == leftMedian && rightMedian != leftMedian){
                firstNum = num;
            }else if (index == rightMedian){
                return (firstNum + num)/2.0;
            }

            index++;
        }
    }

    /**
     * 通过递归的方式实现.
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArraysByRecursive(int[] nums1, int[] nums2) {
        if (nums1.length == 0 && nums2.length == 0) return 0;
        int leftMedian = (nums1.length+nums2.length+1)/2;
        int rightMedian = (nums1.length+nums2.length+2)/2;

        if (nums1.length == 0){
            return (nums2[leftMedian - 1]+ nums2[rightMedian - 1]) / 2.0;
        }else if(nums2.length == 0){
            return (nums1[leftMedian - 1]+ nums1[rightMedian - 1]) / 2.0;
        }

        if (rightMedian != leftMedian){
            return (getMedianNum(nums1,0,nums2,0,leftMedian)+ getMedianNum(nums1,0,nums2,0,rightMedian))/2.0;
        }else{
            return getMedianNum(nums1,0,nums2,0,leftMedian);
        }
    }

    public static double getMedianNum(int[] nums1, int start1, int[]nums2, int start2, int index){
        if(start1 > nums1.length-1) return nums2[start2+index-1];
        if(start2 > nums2.length-1) return nums1[start1+index-1];
        if(index == 1) return Math.min(nums1[start1],nums2[start2]);

        if(start2+index/2-1 > nums2.length-1){
            return getMedianNum(nums1,start1+index/2,nums2,start2,index-index/2);
        }
        if(start1+index/2-1 > nums1.length-1){
            return getMedianNum(nums1,start1,nums2,start2+index/2,index-index/2);
        }

        if(nums1[start1+index/2-1] < nums2[start2+index/2-1]){
            return getMedianNum(nums1,start1+index/2,nums2,start2,index-index/2);
        }else{
            return getMedianNum(nums1,start1,nums2,start2+index/2,index-index/2);
        }
    }

    public static void main(String[] args){
        int[] nums1 = {1,3};
        int[] nums2 = {2};

//        int[] nums1 = {1,3};
//        int[] nums2 = {2};
        long startTime = System.nanoTime();
        double median = findMedianSortedArraysByMerge(nums1, nums2);
        long endTime = System.nanoTime();
        long time = endTime - startTime;
        System.out.println(" median:"+median +" time:"+ time);

        startTime = System.nanoTime();
        median = findMedianSortedArraysByRecursive(nums1, nums2);
        endTime = System.nanoTime();
        time = endTime - startTime;
        System.out.println(" median:"+median +" time:"+ time);
    }

}
