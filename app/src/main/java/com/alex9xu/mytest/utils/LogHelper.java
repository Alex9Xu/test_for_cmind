package com.alex9xu.mytest.utils;

import android.util.Log;

import com.alex9xu.mytest.config.AppConfig;

/**
 * Created by Alex9Xu@hotmail.com on 2016/10/25.
 */

public class LogHelper {
    // Whether show log
    private static boolean isLog = AppConfig.isDebug;

    private static String logTag = "logTag";

    public static void i(String tag, String msg) {
        if (isLog) {
            if (tag == null) {
                tag = logTag;
            }
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isLog) {
            if (tag == null) {
                tag = logTag;
            }
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String value, String ext) {
        if (isLog) {
            if (tag == null) {
                tag = logTag;
            }
            Log.d(tag, value + "--->" + ext);
        }
    }

    public static void v(String tag, String msg) {
        if (isLog) {
            if (tag == null) {
                tag = logTag;
            }
            Log.v(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isLog) {
            if (tag == null) {
                tag = logTag;
            }
            Log.e(tag, msg);
        }
    }


    public static void e(String tag, String value, String msg) {
        if (isLog) {
            if (tag == null) {
                tag = logTag;
            }
            Log.e(tag, value + "--->" + msg);
        }
    }
    public static void i(String tag, String value, String ext) {
        if (isLog) {
            if (tag == null) {
                tag = logTag;
            }
            Log.i(tag, value + "--->" + ext);
        }
    }

}

