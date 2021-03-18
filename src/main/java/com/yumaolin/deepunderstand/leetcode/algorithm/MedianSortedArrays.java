package com.yumaolin.deepunderstand.leetcode.algorithm;

/**
 * @author yml
 * @Description
 * @Date 2021-03-18 16:44
 */
public class MedianSortedArrays {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length = Math.min(nums1.length,nums2.length);
        int nums1Index = 0;
        int nums2Index = 0;
        for(int i=0;i<length;i++){
            int num1 = nums1[nums1Index];
            int num2 = nums2[nums2Index];
            if(num1 < num2){
                ++nums1Index;
            }else if(num1 > num2){
                ++nums2Index;
            }else {
                ++nums1Index;
                ++nums2Index;
            }
            if((nums1Index + nums2Index) == length){
                return (num1 < num2 ? num1 : num2)/2;
            }
        }
        return 0;
    }


    public static void main(String[] args) {
        MedianSortedArrays medianSortedArrays = new MedianSortedArrays();
        int[] nums1 = new int[]{1,3};
        int[] nums2 = new int[]{2};
        System.out.println(medianSortedArrays.findMedianSortedArrays(nums1,nums2));
    }
}
