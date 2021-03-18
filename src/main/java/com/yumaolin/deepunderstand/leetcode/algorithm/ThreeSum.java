package com.yumaolin.deepunderstand.leetcode.algorithm;

import java.util.*;

/**
 * @author yml
 * @Description 三数之和
 * @Date 2021-03-18 17:52
 */
public class ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new LinkedList<>();
        if(nums == null || nums.length < 3){
            return list;
        }
        Arrays.sort(nums);
        for(int i=0,k=nums.length;i<k;i++){
            int num1 = nums[i];
            if(num1 > 0){
                return list;
            }
            if(i >0 && num1 == nums[i-1]){
                continue;
            }
            int left = i+1;
            int right = k-1;
            while(left < right){
                int numLeft = nums[left];
                int numRight = nums[right];
                int sum = num1 + nums[left] + nums[right];
                if(sum == 0){
                    List<Integer> list1 = new ArrayList<>(3);
                    list1.add(Integer.valueOf(num1));
                    list1.add(Integer.valueOf(numLeft));
                    list1.add(Integer.valueOf(numRight));
                    list.add(list1);
                    while (left<right && nums[left] == nums[left+1]){
                        ++left;
                    }
                    while (left<right && nums[right] == nums[right-1]){
                        --right;
                    }
                    ++left;
                    --right;
                }else if(sum > 0){
                    --right;
                }else {
                    ++left;
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-1,0,1,2,-1,-4};
        ThreeSum threeSum  = new ThreeSum();
        threeSum.threeSum(nums).forEach(a->{System.out.println(a);});
    }
}
