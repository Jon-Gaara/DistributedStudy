package com.yumaolin.deepunderstand.leetcode.algorithm;

/**
 * @author yml
 * @Description 盛最多水的容器
 * @Date 2021-03-18 19:03
 */
public class MaxArea {

    public int maxArea(int[] height) {
        int maxArea = 0;
        int left = 0;
        int right = height.length-1;
        while (left < right){
            int heightLeft = height[left];
            int heightRight = height[right];
            int area = (right-left)*Math.min(heightLeft,heightRight);
            maxArea = Math.max(maxArea,area);
            if(heightLeft < heightRight){
                while (heightLeft >= height[left]){
                    ++left;
                }
            }else {
                while (right > left && heightRight >= height[right]){
                    --right;
                }
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        int[] height = new int[]{2,3,4,5,18,17,6};
        MaxArea maxArea = new MaxArea();
        System.out.println(maxArea.maxArea(height));
    }
}
