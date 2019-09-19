package com.yumaolin.deepunderstand.leetcode.algorithm;

import java.util.HashMap;
import java.util.Map;


/**
 * 70. 爬楼梯
 * @author yuml
 * @since 2019年9月18日
 */
public class ClimbStairs {

	private static final Map<Integer, Integer> map = new HashMap<Integer, Integer>(32);

	public int climbStairs(int n) {
		if (n == 0 || n == 1) {
			return 1;
		}
		Integer a = map.get(n);
		if (a == null) {
			a = climbStairs(n - 1) + climbStairs(n - 2);
			map.put(n, a);
		}
		return a;
	}

	/**
	 * <p>用斐波那契公式计算</p>
	 * @param n
	 * @return
	 */
	public int climbStairs2(int n) {
		if (n == 1) {
			return 1;
		}
		int first = 1;
		int second = 2;
		for (int i = 3; i <= n; i++) {
			int third = first + second;
			first = second;
			second = third;
		}
		return second;
	}

	public static void main(String[] args) {
		ClimbStairs climbStairs = new ClimbStairs();
		long start = System.currentTimeMillis();
		System.out.println(climbStairs.climbStairs(44));// 1134903170
		System.out.println(System.currentTimeMillis() - start);
	}
}
