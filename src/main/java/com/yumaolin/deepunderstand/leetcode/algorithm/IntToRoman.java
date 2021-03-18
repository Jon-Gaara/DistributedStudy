package com.yumaolin.deepunderstand.leetcode.algorithm;

import java.util.Arrays;

/**
 * @author yml
 * @Description 整数转罗马数字
 * @Date 2021-03-18 18:22
 */
public class IntToRoman {

    public String intToRoman(int num) {
        int[] digits = new int[4];
        int remainder = num;
        StringBuilder stringBuilder = new StringBuilder(10);
        for(int i=0;i<4;i++){
            int yu = remainder%10;
            digits[i] = yu;
            remainder = remainder/10;
            if(remainder <= 0){
                break;
            }
        }
        System.out.println(Arrays.toString(digits));
        stringBuilder.append(qian(digits[3]));
        stringBuilder.append(bai(digits[2]));
        stringBuilder.append(shi(digits[1]));
        stringBuilder.append(ge(digits[0]));
        return stringBuilder.toString();
    }

    private String qian(int num){
        if(num == 0){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<num;i++){
            stringBuilder.append("M");
        }
        return stringBuilder.toString();
    }

    private String bai(int num){
        if(num == 0){
            return "";
        }
        if(num == 9){
            return "CM";
        }else if(num == 4){
            return "CD";
        }
        StringBuilder stringBuilder = new StringBuilder(5);
        if(num >= 5){
            stringBuilder.append("D");
            num-=5;
        }
        for(int i=0;i<num;i++){
            stringBuilder.append("C");
        }
        return stringBuilder.toString();
    }

    private String shi(int num){
        if(num == 0){
            return "";
        }
        if(num == 9){
            return "XC";
        }else if(num == 4){
            return "XL";
        }
        StringBuilder stringBuilder = new StringBuilder(5);
        if(num >= 5){
            stringBuilder.append("L");
            num-=5;
        }
        for(int i=0;i<num;i++){
            stringBuilder.append("X");
        }
        return stringBuilder.toString();
    }

    private String ge(int num){
        if(num == 0){
            return "";
        }
        if(num == 9){
            return "IX";
        }else if(num == 4){
            return "IV";
        }
        StringBuilder stringBuilder = new StringBuilder(5);
        if(num >= 5){
            stringBuilder.append("V");
            num-=5;
        }
        for(int i=0;i<num;i++){
            stringBuilder.append("I");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        IntToRoman intToRoman = new IntToRoman();
        System.out.println(intToRoman.intToRoman(10));
    }
}
