package com.alex9xu.mytest.utils;

/**
 * Created by Alex9Xu@hotmail.com on 2016/10/26
 */

public class StringUtils {

    public static boolean isStringEmpty(String str) {
        if( null == str || str.equals("") || str.equals("null") ) {
            return true;
        }

        return false;
    }

}
