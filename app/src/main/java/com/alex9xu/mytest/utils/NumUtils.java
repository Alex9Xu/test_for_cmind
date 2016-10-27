package com.alex9xu.mytest.utils;

import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * Created by Alex9Xu@hotmail.com on 2016/10/26
 */

public class NumUtils {
    public static String countNum(String num) {
        char[] chars = num.toCharArray();

        Map<Character, Integer> countMap = new ArrayMap<>();

        for(Character obj: chars){
            if(countMap.containsKey(obj)){
                countMap.put(obj, countMap.get(obj) + 1);
            }else{
                countMap.put(obj, 1);
            }
        }

        StringBuilder strBld = new StringBuilder();
        for (ArrayMap.Entry<Character, Integer> entry : countMap.entrySet()) {
            strBld.append(entry.getValue());
            strBld.append(" times: ");
            strBld.append(entry.getKey());
            strBld.append(", ");
        }
        strBld.delete(strBld.length()-2, strBld.length());
        return strBld.toString();
    }

    public static String getRandomNums(int length) {
        StringBuilder strBld = new StringBuilder();

        for(int i=0; i<length; i++) {
            // Random number for 0 - 9
            int num = (int) (Math.random() * 10);
            strBld.append(num);
        }

        return strBld.toString();
    }

}
