package com.yumaolin.deepunderstand.leetcode.algorithm;

import java.util.ArrayList;
import java.util.List;


/**
 * 78.子集
 * @author yuml
 * @since 2019年9月19日
 */
public class Subsets {

	public List<List<Integer>> subsets(int[] nums) {
		int length = nums.length;
		List<List<Integer>> list = new ArrayList<>((int) Math.pow(2,length));
		ArrayList<ArrayList<Integer>> temp = null;
		list.add(new ArrayList<Integer>());
		for (int i = 0; i < length; i++) {
			temp = new ArrayList<ArrayList<Integer>>(list.size());
			for (int j = 0,k=list.size(); j < k; j++) {
				ArrayList<Integer> element = new ArrayList<Integer>();
				element.add(nums[i]);
				element.addAll(list.get(j));
				temp.add(element);
			}
			list.addAll(temp);
		}
		return list;
	}

	public static void main(String[] args) {
		Subsets subsets = new Subsets();
		List<List<Integer>> list = subsets.subsets(new int[] {1,2,3});
		System.out.println(list);
	}
}
