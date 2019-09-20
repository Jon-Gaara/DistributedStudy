package com.yumaolin.deepunderstand.leetcode.algorithm;

import java.util.Arrays;

/** 
 * 59. 螺旋矩阵 II
 * @author yuml
 * @since 2019年9月19日
 */
public class GenerateMatrix {
	
	public int[][] generateMatrix(int n) {
		int[][] result = new int[n][n];
		int count = 0;
        for(int i=0;i<n;i++) {
        	for(int j=i;j<n-i-1;j++) {
        		result[i][j] = ++count;
        	}
        }
        return result;
    }
	
	public static void main(String[] args) {
		GenerateMatrix generateMatrix = new GenerateMatrix();
		int[][] b = generateMatrix.generateMatrix(3);
		System.out.println(Arrays.deepToString(b));
	}
}
