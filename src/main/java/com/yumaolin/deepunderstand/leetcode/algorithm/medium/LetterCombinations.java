package com.yumaolin.deepunderstand.leetcode.algorithm.medium;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yml
 * @Description 电话号码组合
 * @Date 2021-03-19 10:15
 */
public class LetterCombinations {

    private final String[] PHONE = new String[]{"abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};

    public List<String> letterCombinations(String digits) {
        if(digits == null || digits.equals("")){
            return Collections.emptyList();
        }
        //获取手机按键位置对应的字母
        char[] digitsArray = digits.toCharArray();
        String[] strArrays = new String[digitsArray.length];
        for(int i=0,k=digitsArray.length;i<k;i++){
            int phoneNum = digitsArray[i]-48;
            String numStr = PHONE[phoneNum-2];
            strArrays[i] = numStr;
        }
        if(strArrays.length == 1){
            List<String> list = new LinkedList<>();
            for(char s : strArrays[0].toCharArray()){
                list.add(Character.toString(s));
            }
            return list;
        }
        digitsArray = null;
        digits = null;
        //组合的下标
        int[] index = new int[strArrays.length];
        List<String> list = new LinkedList<>();
        while (true) {
            //如果组合的第一个下标大于等于index.length,说明已经完成了
            if(index[0] >= strArrays[0].length()){
                break;
            }
            int indexLength = index.length;
            //循环获取组合
            StringBuilder stringBuilder = new StringBuilder(indexLength);
            for (int i = 0; i < indexLength; i++) {
                stringBuilder.append(strArrays[i].charAt(index[i]));
            }
            list.add(stringBuilder.toString());
            for(int j=indexLength-1;j>=0;j--){
                if(index[j] < strArrays[j].length()-1){
                    index[j]+=1;
                    break;
                }else{
                    if(j == 0){
                        index[j]+=1;
                    }else {
                        index[j] = 0;
                    }
                    continue;
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        LetterCombinations letterCombinations = new LetterCombinations();
        System.out.println(letterCombinations.letterCombinations("2"));
    }
}
