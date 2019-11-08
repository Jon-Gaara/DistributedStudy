package com.yumaolin.deepunderstand.leetcode.algorithm;

/**
 * 70. 爬楼梯
 * @author yuml
 * @since 2019年9月18日
 */
public class ClimbStairs {

	private int[] cache = null;

	public int climbStairs(int n) {
		if(cache == null) {
			cache = new int[n];
		} else {
			int length = cache.length;
			if(length < n) {
				int[] newCache = new int[n];
				System.arraycopy(cache, 0, newCache, 0, cache.length);
				cache = newCache;
			}
		}
		if (n == 0 || n == 1) {
			return 1;
		}
		int a = cache[n-1];
		if (a == 0) {
			a = climbStairs(n - 1) + climbStairs(n - 2);
			cache[n-1] = a;
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
		System.out.println(climbStairs.climbStairs(88));
		System.out.println(climbStairs.climbStairs2(88));
		System.out.println(System.currentTimeMillis() - start);
	}
}
