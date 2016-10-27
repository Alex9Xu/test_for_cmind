package com.alex9xu.mytest.utils;

import android.support.v4.util.ArrayMap;

import com.alex9xu.mytest.model.NumCounter;

import java.util.ArrayList;
import java.util.List;
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

    public static String countNumType2(String num) {
        char[] chars = num.toCharArray();

        List<NumCounter> counterList = new ArrayList<>();

        for(int i=0; i<chars.length; i++) {
            int counter = 0;
            for(int j=i; j< chars.length; j++) {
                if(chars[i] == chars[j]) {
                    counter++;
                } else {
                    NumCounter numCounter = new NumCounter();
                    numCounter.setTimes(numToWord(counter));
                    if(counter > 1) {
                        numCounter.setNumber(chars[i] + "s");
                    } else {
                        numCounter.setNumber(String.valueOf(chars[i]));
                    }
                    counterList.add(numCounter);
                    break;
                }
            }
        }

        StringBuilder strBld = new StringBuilder();
        for (NumCounter numCounter : counterList) {
            strBld.append(numCounter.getTimes());
            strBld.append(" ");
            strBld.append(numCounter.getNumber());
            strBld.append(", ");
        }
        strBld.delete(strBld.length()-2, strBld.length());
        return strBld.toString();
    }

    public static String numToWord(int num) {
        String word = "";
        switch (num) {
            case 0:
                word = "zero";
                break;
            case 1:
                word = "one";
                break;
            case 2:
                word = "two";
                break;
            case 3:
                word = "three";
                break;
            case 4:
                word = "four";
                break;
            case 5:
                word = "five";
                break;
            case 6:
                word = "six";
                break;
            case 7:
                word = "seven";
                break;
            case 8:
                word = "eight";
                break;
            case 9:
                word = "nine";
                break;
            default:
                break;
        }

        return  word;
    }

}
